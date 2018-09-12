package com.example.eloem.dartCounter.helperClasses

import android.os.Parcelable
import com.example.eloem.dartCounter.helperClasses.games.DartGame
import emil.beothy.utilFun.containsAllAlsoMultiple
import emil.beothy.utilFun.deepCopy
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class Turn(val points: @RawValue Array<DartGame.Point>): Parcelable{
    val pointsScored: Int get() = points.sumBy { it.value }
    
    val alreadyThrown: Turn get() {
        val list = mutableListOf<DartGame.Point>()
    
        points.forEachIndexed { index, point ->
            if (point.value == 0){
                if (index + 1 < points.size){
                    if (points[index + 1].value != 0) list.add(points[index])
                }
            }else list.add(points[index])
        }
        return Turn(list.toTypedArray())
    }
    
    val lastThrow: DartGame.Point? get() = points.findLast { it.value > 0 }
    
    operator fun compareTo(other: Turn): Int = if (points.size == other.points.size){
            if (points.containsAllAlsoMultiple(other.points)) 0
            else pointsScored - other.pointsScored
        }else points.size - other.points.size
    
    fun deepCopy() = Turn(points.deepCopy { it.copy() })
    
    override fun toString(): String = points.joinToString()
    
    operator fun get(position: Int): DartGame.Point = points[position]
    
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