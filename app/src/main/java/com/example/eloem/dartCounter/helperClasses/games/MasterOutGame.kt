package com.example.eloem.dartCounter.helperClasses.games

import com.example.eloem.dartCounter.helperClasses.Player
import emil.beothy.utilFun.deepCopy
import java.util.*

class MasterOutGame(id: Int, players: Array<Player>, pPos: Int = 0, date: Date = Calendar.getInstance().time):
        OutGame(id, players, pPos, date) {
    
    override fun validateLastThrow(lastThrow: Point): Boolean = lastThrow.multiplicator != 1
    
    override fun copy(pId: Int, pPlayers: Array<Player>, pCurrentPlayerPos: Int, pDate: Date): DartGame {
        return MasterOutGame(pId, pPlayers, pCurrentPlayerPos, pDate)
    }
}