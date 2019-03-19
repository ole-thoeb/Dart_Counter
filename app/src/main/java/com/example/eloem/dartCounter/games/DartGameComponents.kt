package com.example.eloem.dartCounter.games

import android.os.Parcelable
import emil.beothy.utilFun.containsAllAlsoMultiple
import emil.beothy.utilFun.copy
import emil.beothy.utilFun.deepCopy
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*


@Parcelize
data class Player(val id: Int,
                  var name: String,
                  var points: Int,
                  val history: @RawValue MutableList<HistoryTurn>): Parcelable {
    
    override fun toString(): String {
        return "$name: $points"
    }
    
    fun deepCopy(pId: Int = id,
                 pName: String = name,
                 pPoints: Int = points,
                 pHistory: MutableList<HistoryTurn> = history): Player {
        
        return Player(pId,
                pName.copy(),
                pPoints,
                pHistory.deepCopy { it.deepCopy() }.toMutableList())
    }
    
    val startingPoints: Int get() = if (history.isNotEmpty()) history.first().pointsBefore else 0
    
    companion object {
        fun empty() = Player(-1, "", 0, mutableListOf())
    }
}

@Parcelize
open class Turn(val points: @RawValue Array<Point>): Parcelable {
    
    val pointsScored: Int get() = points.sumBy { it.value }
    
    val alreadyThrown: Turn
        get() {
            val list = mutableListOf<Point>()
            
            points.forEachIndexed { index, point ->
                if (point.value == 0){
                    val nextIndex = index + 1
                    if (nextIndex < points.size && points[nextIndex].value != 0) {
                        list.add(points[nextIndex])
                    }
                }else list.add(points[index])
            }
            return Turn(list.toTypedArray())
        }
    
    val lastThrow: Point? get() = points.findLast { it.value > 0 }
    
    operator fun compareTo(other: Turn): Int = if (points.size == other.points.size){
        if (points.containsAllAlsoMultiple(other.points)) 0
        else pointsScored - other.pointsScored
    }else points.size - other.points.size
    
    open fun deepCopy() = Turn(points.deepCopy { it.copy() })
    
    override fun toString(): String = points.joinToString()
    
    operator fun get(position: Int): Point = points[position]
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        
        other as Turn
        
        if (points.containsAllAlsoMultiple(other.points) && points.size == other.points.size) return true
        
        return false
    }
    
    override fun hashCode(): Int {
        return Arrays.hashCode(points)
    }
}

class HistoryTurn(points: Array<Point>, val pointsAfter: Int, val pointsBefore: Int):
        Turn(points) {
    
    override fun deepCopy() = HistoryTurn(points.deepCopy { it.copy() }, pointsAfter, pointsBefore)
}

@Suppress("DataClassPrivateConstructor")
data class Point private constructor(val id: Int, val multiplicator: Int, val point: Int){
    
    init {
        require(multiplicator in 1..3) { "multiplicator must between 1 and 3 but is $multiplicator" }
        require(point in 0..20 || point == 25) { "point must between 1 and 20 but is $point" }
    }
    
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
        
        fun instanceByPoints(multiplicator: Int, point: Int): Point {
            return allPossiblePoints.find {
                it.multiplicator == multiplicator && it.point == point
            } ?: throw Error("$multiplicator, $point is not a valid point!")
        }
        
        fun instanceById(id: Int?): Point? = id?.let { instanceById(it) }
    }
}