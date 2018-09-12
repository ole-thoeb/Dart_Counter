package com.example.eloem.dartCounter.util

import android.content.Context
import android.preference.PreferenceManager

/*const val CURRENT_GAME_ID = "com.example.eloem.dartCounter.currentGame"
const val CURRENT_GAME_ID_PLAYER_POS = "$CURRENT_GAME_ID.playerPos"
const val CURRENT_GAME_ID_PLAYERS = "$CURRENT_GAME_ID.players"
const val CURRENT_GAME_ID_MODE = "$CURRENT_GAME_ID.mode"

fun writeCurrentGame(context: Context, game: DartGame){
    val pM =PreferenceManager.getDefaultSharedPreferences(context).edit()
    val gson = Gson()
    
    fun writeCurrentPlayerPos(currentPlayerPos: Int){
        pM.putInt(CURRENT_GAME_ID_PLAYER_POS, currentPlayerPos).apply()
    }
    
    fun writePlayers(players: Array<Player>){
        val json = gson.toJson(players)
        pM.putString(CURRENT_GAME_ID_PLAYERS, json).apply()
    }
    
    fun writeMode(mode: String){
        pM.putString(CURRENT_GAME_ID_MODE, mode)
    }
    
    writeCurrentPlayerPos(game.currentPlayerPos)
    writePlayers(game.players)
    writeMode(game.mode)
}

fun readCurrentGame(context: Context): DartGame{
    val pM =PreferenceManager.getDefaultSharedPreferences(context)
    val gson = Gson()
    
    fun readCurentPlayerPos(): Int{
        return pM.getInt(CURRENT_GAME_ID_PLAYER_POS, 0)
    }
    
    fun readPlayers(): Array<Player>{
        val json = pM.getString(CURRENT_GAME_ID_PLAYERS, "")
        return gson.fromJson(json, Array<Player>::class.java)
    }
    
    fun readMode(): String{
        return pM.getString(CURRENT_GAME_ID_MODE, "")
    }
    
    val dartGame = DartGame(readPlayers(), readMode())
    dartGame.currentPlayerPos = readCurentPlayerPos()
    return dartGame
}

const val THERE_IS_GAME_ID = "com.example.eloem.dartCounter.isGame"

fun writeThereIsGame(context: Context, value: Boolean){
    val pM =PreferenceManager.getDefaultSharedPreferences(context).edit()
    pM.putBoolean(THERE_IS_GAME_ID, value).apply()
}

fun readThereIsGame(context: Context): Boolean{
    val pM =PreferenceManager.getDefaultSharedPreferences(context)
    return pM.getBoolean(THERE_IS_GAME_ID, false)
}*/

const val GAME_ID_ID = "gameID"

fun newGameID(context: Context):Int{
    val id = getPrefInt(context, GAME_ID_ID)
    putPrefInt(context, GAME_ID_ID, id + 1)
    return id
}

const val PLAYER_ID_ID = "playerID"

fun newPlayerID(context: Context):Int{
    val id = getPrefInt(context, PLAYER_ID_ID)
    putPrefInt(context, PLAYER_ID_ID, id + 1)
    return id
}

const val THROW_ID_ID = "throwID"

fun newThrowID(context: Context):Int{
    val id = getPrefInt(context, THROW_ID_ID)
    putPrefInt(context, THROW_ID_ID, id + 1)
    return id
}

fun putPrefInt(context: Context, key: String, value: Int) = with(PreferenceManager.getDefaultSharedPreferences(context).edit()){
    putInt(key, value).apply()
}

fun getPrefInt(context: Context, key: String) = with(PreferenceManager.getDefaultSharedPreferences(context)){
    return@with getInt(key, 0)
}