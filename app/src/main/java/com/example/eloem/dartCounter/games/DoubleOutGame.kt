package com.example.eloem.dartCounter.games

import java.util.*

class DoubleOutGame(id: Int, players: Array<Player>, pPos: Int = 0, date: Date = Calendar.getInstance().time):
        OutGame(id, players, pPos, date) {
    
    override fun validateLastThrow(lastThrow: Point): Boolean = lastThrow.multiplicator == 2
    
    override fun copy(pId: Int, pPlayers: Array<Player>, pCurrentPlayerPos: Int, pDate: Date): DartGame {
        return DoubleOutGame(pId, pPlayers, pCurrentPlayerPos, pDate)
    }
}