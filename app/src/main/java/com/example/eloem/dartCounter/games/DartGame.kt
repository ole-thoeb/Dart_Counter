package com.example.eloem.dartCounter.games

import emil.beothy.utilFun.deepCopy
import java.util.*

abstract class DartGame(val id: Int,
                        val players: Array<Player>,
                        var currentPlayerPos: Int = 0,
                        val date: Date = Calendar.getInstance().time) {
    
    /**
     * callback triggered then the game ends. called with the winners @see [winners]
     */
    var onGameFinish: ((List<Player>) -> Unit)? = null
    
    val currentPlayer: Player
        get() = players[currentPlayerPos]
    
    val previousPlayer: Player
        get() = if (currentPlayerPos -1 < 0) players[ players.size - 1] else players[currentPlayerPos  - 1]
    
    val isFinished: Boolean
        get() = isDraw || isWon
    
    val state get() = when {
        isDraw -> STATE_DRAW
        isWon -> STATE_WINNER
        else -> STATE_ON_GOING
    }
    
    abstract val isWon: Boolean
    abstract val isDraw: Boolean
    /**
     * List containing only the winner or all the player that tied
     * */
    abstract val winners: List<Player>
    
    abstract fun nextPlayerThrow(throws: Turn, commit: Boolean): Array<Player>
    
    fun nextPlayer(){
        currentPlayerPos ++
        currentPlayerPos %= players.size
        if (currentPlayerPos == 0 && isFinished) onGameFinish?.invoke(winners)
    }
    
    abstract fun copy(pId: Int = id, pPlayers: Array<Player> = players,
                      pCurrentPlayerPos: Int = currentPlayerPos, pDate: Date = date): DartGame
    
    companion object {
        
        enum class STATE{ DRAW, WINNER, ON_GOING }
        const val STATE_DRAW = "drawState"
        const val STATE_WINNER = "winnerState"
        const val STATE_ON_GOING = "onGoingState"
        
        val allThrowCombinations by lazy {
            val possibleMoves = Point.allPossiblePoints
    
            val closingMoves = mutableListOf<Turn>()
    
            possibleMoves.forEachIndexed { index, point ->
                val move = mutableListOf<Point>()
        
                move.add(point)
        
                for (i in index.until(possibleMoves.size)) {
                    val move1 = move.deepCopy { it.copy() }.toMutableList()
            
                    move1.add(possibleMoves[i])
            
                    for (j in i.until(possibleMoves.size)) {
                        val move2 = move1.deepCopy { it.copy() }.toMutableList()
                
                        move2.add(possibleMoves[j])
                        
                        move2.sortBy { it.value }
                
                        closingMoves.add(Turn(move2.toTypedArray()))
                    }
                }
            }
            closingMoves.distinct().toList()
        }
    }
}