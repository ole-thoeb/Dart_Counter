package com.example.eloem.dartCounter.games

import emil.beothy.utilFun.containsAllAlsoMultiple
import emil.beothy.utilFun.deepCopy
import emil.beothy.utilFun.dropToNewSize
import java.util.*

abstract class OutGame(id: Int, players: Array<Player>, pPos: Int, date: Date):
        DartGame(id, players, pPos, date){
    
    override fun nextPlayerThrow(throws: Turn, commit: Boolean): Array<Player> {
        val totalPoints: Int = throws.pointsScored
        var playerPoints: Int = currentPlayer.points - totalPoints
    
        if (playerPoints < 0) playerPoints = currentPlayer.points // reset Points
        else if (playerPoints == 0) {
            val lastThrow = throws.lastThrow
        
            if (lastThrow != null && !validateLastThrow(lastThrow)){
                playerPoints = currentPlayer.points //reset Points
            }
        }
    
        val temporaryPlayerStanding = players.deepCopy { it.deepCopy() }
        temporaryPlayerStanding[currentPlayerPos].points = playerPoints
    
        if (commit){
            val pointsBefore = currentPlayer.points
            currentPlayer.points = playerPoints
            currentPlayer.history.add(HistoryTurn(throws.points, playerPoints, pointsBefore))
            nextPlayer()
        }
    
        return temporaryPlayerStanding
    }
    
    override val isDraw: Boolean get() = players.count {it.points == 0} > 1
    
    override val isWon: Boolean get() = players.count { it.points == 0 } == 1
    
    override val winners: List<Player> get() = players.filter { it.points == 0 }
    
    /**
     * @returns true if the given throw would result in the current player winning
     */
    abstract fun validateLastThrow(lastThrow: Point): Boolean
    
    fun calculateClosingThrows(currentState: Turn): List<Turn>{
        var possibleThrows = allThrowCombinations
        val alreadyScored = currentState.alreadyThrown
        
        possibleThrows = possibleThrows.filter { turn ->
            turn.points.sumBy { it.value } == currentPlayer.points &&
                    turn.points.containsAllAlsoMultiple(alreadyScored.points) &&
                    turn.points.any{ validateLastThrow(it) && it !in alreadyScored.points}
        }
        
        possibleThrows = possibleThrows.toMutableList()
        
        //remove options with 3 multiplicators
        possibleThrows = possibleThrows.dropToNewSize(10) { turn ->
            turn.points.all { it.multiplicator > 1 }
        }
        
        //remove options with 2 multiplicators
        possibleThrows = possibleThrows.dropToNewSize(10) {
            turn -> turn.points.count { it.multiplicator > 1 } == 2
        }
        
        //remove options with 1 multiplicators
        possibleThrows = possibleThrows.dropToNewSize(10) { turn ->
            turn.points.count{ it.multiplicator > 1 } == 1 &&
                turn.points.count { it.point == 0 } != 2 // if only one field to hit it stays
        }
        
        //remove options were all have to hit
        possibleThrows = possibleThrows.dropToNewSize(10) { turn ->
            turn.points.all { it.value != 0}
        }
        
        possibleThrows = possibleThrows.sortedByDescending { turn ->
            turn.points.count { it.value == 0 } -
                2 * turn.points.count { it.multiplicator > 1 }
        }
        
        return possibleThrows
    }
}