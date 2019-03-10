package com.example.eloem.dartCounter

import com.example.eloem.dartCounter.games.DartGame
import com.example.eloem.dartCounter.games.Player
import com.example.eloem.dartCounter.games.Point
import emil.beothy.utilFun.containsAllAlsoMultiple
import emil.beothy.utilFun.minus
import emil.beothy.utilFun.removeAllSubstrings
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun stringMinus_isCorrect(){
        assertEquals("e", "ere" - "er")
    }
    @Test
    fun removeAllSubstrings_isCorrect(){
        assertEquals("aassdfg", "aassdfg".removeAllSubstrings(""))
    }
    @Test
    fun test(){
        var possibleThrows = DartGame.allThrowCombinations
    
        possibleThrows = possibleThrows.filter {turn ->
            turn.points.sumBy { it.value } == 92
        }
        possibleThrows = possibleThrows.filter {turn ->
            turn.points.containsAllAlsoMultiple(arrayOf(Point.instanceByPoints(1, 0),
                    Point.instanceByPoints(3, 20)))
        }
        possibleThrows
    }
    @Test
    fun containsAllAlsoMultiple_isCorrect(){
        val a = List(20){ Point.instanceById(it +40)}
        val b = List(10){ Point.instanceById(it + 50)}
        assertEquals(true, a.containsAllAlsoMultiple(b))
    }
    @Test
    fun alreadyThrown_isCorrect(){
        val a = Turn(arrayOf(Point.instanceByPoints(2, 20),
                Point.instanceByPoints(1, 20),
                Point.instanceByPoints(1, 0)))
        val b = a.alreadyThrown
        val c = Turn(arrayOf(Point.instanceByPoints(1, 20),
                Point.instanceByPoints(2, 20)))
        assertEquals(true, b == c)
    }
    @Test
    fun test2(){
        val a = Turn(arrayOf(Point.instanceByPoints(1, 20),
                Point.instanceByPoints(2, 20)))
        val b = Turn(arrayOf(Point.instanceByPoints(1, 20),
                Point.instanceByPoints(2, 20)))
        assertEquals(true, a == b)
    }
    @Test
    fun testInGame(){
        val game = DartGame(1, arrayOf(Player(1, "E", 92, arrayListOf())), DartGame.MODE_SINGLE_OUT)
        
        val a = game.closingThrows(Turn(arrayOf(Point.instanceByPoints(1, 0),
                Point.instanceByPoints(1, 20))))
        val b = game.closingThrows(Turn(arrayOf(Point.instanceByPoints(1, 0),
                Point.instanceByPoints(2, 20))))
        val c = game.closingThrows(Turn(arrayOf(Point.instanceByPoints(1, 0),
                Point.instanceByPoints(3, 20))))
    }
    @Test
    fun test3(){
        println(DartGame.closingThrows(Turn(arrayOf(Point.instanceByPoints(1, 17),
                Point.instanceByPoints(3, 20),
                Point.instanceByPoints(2, 11))),99))
    }
}
