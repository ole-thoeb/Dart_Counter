package com.example.eloem.dartCounter.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.eloem.dartCounter.helperClasses.*
import com.example.eloem.dartCounter.helperClasses.games.*
import org.jetbrains.anko.db.*
import java.sql.SQLException
import java.util.*

fun deleteAllGamesComplete(context: Context){
    // muss getrent sein
    context.database.use { dropAllTables(this) }
    context.database.use { createTables(this) }
}

fun deleteCompleteGame(context: Context, game: DartGame) = context.database.use {
    deleteGame(context, game.id)
    
    deletePlayersFromOneGame(context, game.id)
    for (player in game.players) {
        deleteThrowsFromOnePlayer(context, player.id)
    }
}

fun deletePlayersFromOneGame(context: Context, gameId: Int) = context.database.use {
    delete(Players.tableName, "${Players.columnGameID} = {id}", "id" to gameId)
}

fun deleteThrowsFromOnePlayer(context: Context, playerId: Int) = context.database.use {
    delete(Turns.tableName, "${Turns.columnPlayerID} = {pId}", "pId" to playerId)
}

fun deleteGame(context: Context, id: Int) = context.database.use {
    delete(Games.tableName, "${Games.columnID} = {id}", "id" to id)
}

fun deletePlayer(context: Context, id: Int) = context.database.use {
    delete(Players.tableName, "${Players.columnID} = {id}", "id" to id)
}

fun getOutGame(context: Context, gameId: Int): OutGame = context.database.use {
    val gameParser = rowParser {id: Int, mode: String, currentPlayerPos: Int, date: Long ->
        val cal = Calendar.getInstance()
        cal.timeInMillis = date
    
        val game = when(mode){
            singleOut -> SingleOutGame(id, getPlayerFromOneGame(context, id), currentPlayerPos, cal.time)
            doubleOut -> DoubleOutGame(id, getPlayerFromOneGame(context, id), currentPlayerPos, cal.time)
            masterOut -> MasterOutGame(id, getPlayerFromOneGame(context, id), currentPlayerPos, cal.time)
            else -> throw SQLException("Unknown mode: $mode")
        }
        
        game
    }
    
    select(Games.tableName,
            Games.columnID,
            Games.columnMode,
            Games.columnCurrentPlayerPos,
            Games.columnDate)
            .whereArgs("${Games.columnID} = {gameId}", "gameId" to gameId)
            .parseSingle(gameParser)
}

fun getAllGames(context: Context) = context.database.use {
    val games = mutableListOf<DartGame>()
    
    val gameParser = rowParser {id: Int, mode: String, currentPlayerPos: Int, date: Long ->
        val cal = Calendar.getInstance()
        cal.timeInMillis = date
    
        val game = when(mode){
            singleOut -> SingleOutGame(id, getPlayerFromOneGame(context, id), currentPlayerPos, cal.time)
            doubleOut -> DoubleOutGame(id, getPlayerFromOneGame(context, id), currentPlayerPos, cal.time)
            masterOut -> MasterOutGame(id, getPlayerFromOneGame(context, id), currentPlayerPos, cal.time)
            else -> throw SQLException("Unknown mode: $mode")
        }
        
        games.add(game)
        
        game
    }
    
    select(Games.tableName,
            Games.columnID,
            Games.columnMode,
            Games.columnCurrentPlayerPos,
            Games.columnDate)
            .parseList(gameParser)
    
    games
}

fun getPlayerFromOneGame(context: Context, gameId: Int) = context.database.use {
    val players = mutableListOf<Player>()
    
    val playerParser = rowParser {id: Int, name: String, points: Int ->
        val player = Player(id, name, points, getPlayerHistory(context, id))
        
        players.add(player)
        
        player
    }
    
    select(Players.tableName,
            Players.columnID,
            Players.columnName,
            Players.columnPoints)
            .whereArgs("${Players.columnGameID} = {id}", "id" to gameId)
            .orderBy(Players.columnPosition)
            .parseList(playerParser)
    
    players.toTypedArray()
}

fun getPlayerHistory(context: Context, playerId: Int) = context.database.use {
    val history = mutableListOf<HistoryTurn>()
    
    val historyParser = rowParser {p1: Int, p2: Int, p3: Int, pLeft: Int, pBefore: Int ->
        val arg = HistoryTurn(arrayOf(DartGame.Point.instanceById(p1),
                DartGame.Point.instanceById(p2),
                DartGame.Point.instanceById(p3)),
                pLeft,
                pBefore)
        
        history.add(arg)
        
        arg
    }
    
    select(Turns.tableName,
            Turns.columnThrowOne,
            Turns.columnThrowTwo,
            Turns.columnThrowThree,
            Turns.columnPointsLeft,
            Turns.columnPointsBefore)
            .whereArgs("${Turns.columnPlayerID} = {id}", "id" to playerId)
            .orderBy(Turns.columnPosition)
            .parseList(historyParser)
    
    history
}

fun insertCompleteGame(context: Context, game: DartGame){
    insertGame(context, game)
    game.players.forEachIndexed { index, player ->
        insertPlayer(context, game, index)
        insertAllTurns(context, player)
    }
}

fun insertGame(context: Context, game: DartGame) = context.database.use {
    insert(Games.tableName,
            Games.columnID to game.id,
            Games.columnMode to getModeOfGame(game),
            Games.columnCurrentPlayerPos to game.currentPlayerPos,
            Games.columnDate to game.date.time)
}

fun insertPlayer(context: Context, game: DartGame, playerPos: Int) = context.database.use {
    insert(Players.tableName,
            Players.columnID to game.players[playerPos].id,
            Players.columnName to game.players[playerPos].name,
            Players.columnGameID to game.id,
            Players.columnPoints to game.players[playerPos].points,
            Players.columnPosition to playerPos)
}

fun insertAllTurns(context: Context, player: Player) = context.database.use {
    player.history.forEachIndexed { index, turn ->
        insertTurn(context, turn, player.id, index)
    }
}

fun insertTurn(context: Context, turn: HistoryTurn, playerId: Int, position: Int) = context.database.use {
    insert(Turns.tableName,
            Turns.columnPlayerID to playerId,
            Turns.columnPosition to position,
            Turns.columnThrowOne to turn[0].id,
            Turns.columnThrowTwo to turn[1].id,
            Turns.columnThrowThree to turn[2].id,
            Turns.columnPointsLeft to turn.pointsAfter,
            Turns.columnPointsBefore to turn.pointsBefore)
}
//should be called after the turn is applied
fun updateNewTurn(context: Context, game: DartGame) = context.database.use {
    update(Players.tableName, Players.columnPoints to game.previousPlayer.points)
            .whereArgs("${Players.columnID} = {id}", "id" to game.previousPlayer.id)
            .exec()
    
    update(Games.tableName, Games.columnCurrentPlayerPos to game.currentPlayerPos)
            .whereArgs("${Games.columnID} = {gId}", "gId" to game.id)
            .exec()
    
    insertTurn(context, game.previousPlayer.history.last(),
            game.previousPlayer.id, game.previousPlayer.history.size)
}

fun createTables(db: SQLiteDatabase) = db.use{
    it.createTable(Games.tableName, true,
            Games.columnID to INTEGER + PRIMARY_KEY + UNIQUE,
            Games.columnMode to TEXT,
            Games.columnCurrentPlayerPos to INTEGER,
            Games.columnDate to INTEGER)
    
    it.createTable(Players.tableName, true,
            Players.columnID to INTEGER + PRIMARY_KEY + UNIQUE,
            Players.columnGameID to INTEGER,
            Players.columnName to TEXT,
            Players.columnPoints to INTEGER,
            Players.columnPosition to INTEGER)
    
    it.createTable(Turns.tableName, true,
            Turns.columnThrowOne to INTEGER,
            Turns.columnThrowTwo to INTEGER,
            Turns.columnThrowThree to INTEGER,
            Turns.columnPlayerID to INTEGER,
            Turns.columnPosition to INTEGER,
            Turns.columnPointsLeft to INTEGER,
            Turns.columnPointsBefore to INTEGER)
}

fun dropAllTables(db: SQLiteDatabase) = db.use {
    it.dropTable(Games.tableName)
    it.dropTable(Players.tableName)
    it.dropTable(Turns.tableName)
}

fun getModeOfGame(game: DartGame) = when(game){
    is SingleOutGame -> singleOut
    is DoubleOutGame -> doubleOut
    is MasterOutGame -> masterOut
    else -> throw Error("Unknown Type")
}

const val singleOut = "sOut"
const val doubleOut = "dOut"
const val masterOut = "mOut"