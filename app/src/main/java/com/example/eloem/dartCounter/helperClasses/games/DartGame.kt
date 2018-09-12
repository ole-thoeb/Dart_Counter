package com.example.eloem.dartCounter.helperClasses.games

import com.example.eloem.dartCounter.helperClasses.Player
import com.example.eloem.dartCounter.helperClasses.Turn
import emil.beothy.utilFun.deepCopy
import java.util.*

abstract class DartGame(val id: Int, val players: Array<Player>,
                        var currentPlayerPos: Int = 0, val date: Date = Calendar.getInstance().time) {
    
    data class Point(val id: Int, val multiplicator: Int, val point: Int){
        
        val value get() = multiplicator * point
        
        operator fun compareTo(otherPoint: Point):Int =
                if (otherPoint.id == id)  0
                    else value - otherPoint.value
        
        override fun toString() = when(multiplicator){
            1 -> "$point"
            2 -> "D$point"
            3 -> "T$point"
            else -> "$point"
        }
        
        companion object {
            val allPossiblePoints: Array<Point> by lazy {
                val moves = mutableListOf<Point>()
                
                for (i in 1..20){
                    for (mult in 1..3){
                        moves.add(Point(((i - 1) * 3) + mult - 1, mult, i))
                    }
                }
                moves.add(Point(60, 1, 0))
                moves.add(Point(61, 1, 25))
                moves.add(Point(62, 2, 25))
                 moves.toTypedArray()
            }
            
            fun instanceById(id: Int): Point = allPossiblePoints[id]
            
            fun instanceByPoints(multiplicator: Int, point: Int): Point
                    = allPossiblePoints.find { it.multiplicator == multiplicator
                    && it.point == point }?.copy()?: throw Error("$multiplicator, $point is not a valid point!")
            
            fun instanceById(id: Int?): Point? = if (id == null) null
                else instanceById(id)
        }
    }
    
    var setGameWonListener: (List<Player>) -> Unit = {_: List<Player> -> Unit}
    
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
    abstract val winner: List<Player>
    
    abstract fun nextPlayerThrow(throws: Turn, commit: Boolean): Array<Player>
    
    fun nextPlayer(){
        currentPlayerPos ++
        currentPlayerPos %= players.size
        if (currentPlayerPos == 0 && isFinished) setGameWonListener(winner)
    }
    
    abstract fun copy(pId: Int = id, pPlayers: Array<Player> = players,
             pCurrentPlayerPos: Int = currentPlayerPos, pDate: Date = date): DartGame
    
    companion object {
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