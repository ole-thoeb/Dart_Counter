package com.example.eloem.dartCounter.helperClasses

open class BetterPoint {
    var x: Double
    var y: Double
    
    constructor(pX: Double, pY: Double){
        this.x = pX
        this.y = pY
    }
    
    constructor(){
        this.x = 0.0
        this.y = 0.0
    }
    
    fun distance(otherPoint: BetterPoint): Double{
        val distX = this.x - otherPoint.x
        val distY = this.y - otherPoint.y
        
        return Math.sqrt((Math.pow(distX, 2.0) + Math.pow(distY, 2.0)))
    }
    
    open operator fun plus(vector: BetterVector): BetterPoint{
        return BetterPoint(this.x + vector.x, this.y + vector.y)
    }
    
    open operator fun minus(vector: BetterVector): BetterPoint{
        return BetterPoint(this.x - vector.x, this.y - vector.y)
    }
    
    override fun toString(): String {
        return "${this.x}, ${this.y}"
    }
}