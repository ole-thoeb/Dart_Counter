package com.example.eloem.dartCounter.helperClasses.games

import com.example.eloem.dartCounter.helperClasses.Player
import emil.beothy.utilFun.deepCopy
import java.util.*

class SingleOutGame(id: Int, players: Array<Player>, pPos: Int = 0, date: Date = Calendar.getInstance().time):
        OutGame(id, players, pPos, date) {
    
    override fun validateLastThrow(lastThrow: Point): Boolean  = true
    
    override fun copy(pId: Int, pPlayers: Array<Player>, pCurrentPlayerPos: Int, pDate: Date): DartGame {
        return SingleOutGame(pId, pPlayers, pCurrentPlayerPos, pDate)
    }
}