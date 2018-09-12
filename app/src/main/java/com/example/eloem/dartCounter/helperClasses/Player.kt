package com.example.eloem.dartCounter.helperClasses

import android.os.Parcelable
import emil.beothy.utilFun.copy
import emil.beothy.utilFun.deepCopy
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Player(val id: Int, var name: String, var points: Int, val history: @RawValue MutableList<HistoryTurn>):Parcelable {
    
    override fun toString(): String {
        return "$name: $points"
    }
    
    fun deepCopy(pId: Int = id,
                 pName: String = name,
                 pPoints: Int = points,
                 pHistory: MutableList<HistoryTurn> = history)
            = Player(pId,
            pName.copy(),
            pPoints,
            pHistory.deepCopy { it.deepCopy() }.toMutableList())
    
    val startingPoints: Int
        get() = if (history.isNotEmpty()){
            history[0].pointsBefore
        } else 0
}