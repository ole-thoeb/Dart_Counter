package com.example.eloem.dartCounter.helperClasses

import com.example.eloem.dartCounter.helperClasses.games.DartGame
import emil.beothy.utilFun.deepCopy

data class HistoryTurn(val points: Array<DartGame.Point>, val pointsAfter: Int, val pointsBefore: Int){
    fun deepCopy() = HistoryTurn(points.deepCopy { it.copy() }, pointsAfter, pointsBefore)
    
    fun toTurn() = Turn(points)
    
    operator fun get(pos: Int): DartGame.Point = points[pos]
    
    val pointsScored: Int get() = points.sumBy { it.value }
}