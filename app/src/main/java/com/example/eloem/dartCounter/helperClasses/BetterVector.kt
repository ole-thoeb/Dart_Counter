package com.example.eloem.dartCounter.helperClasses

open class BetterVector: BetterPoint{
    
    constructor(point1: BetterPoint, point2: BetterPoint){
        this.x = point2.x - point1.x
        this.y = point2.y - point1.y
    }
    
    constructor(pX: Double, pY: Double){
        this.x = pX
        this.y = pY
    }
    
    constructor(){
        this.x = 0.0
        this.y = 0.0
    }
    
    val length get() = Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0))
    
    fun rotate(angel: Double): BetterVector {
        val rotatedVector = BetterVector()
        
        val angelRadian = Math.toRadians(angel)
        
        val cos = Math.cos(angelRadian)
        val sin = Math.sin(angelRadian)
        
        rotatedVector.x = cos * this.x + sin * this.y
        rotatedVector.y = -sin * this.x + cos * this.y
        
        return rotatedVector
    }
    
    open operator fun plus(vector: BetterPoint): BetterPoint =
            BetterPoint(this.x + vector.x, this.y + vector.y)
    
    open operator fun minus(vector: BetterPoint): BetterPoint =
            BetterPoint(this.x - vector.x, this.y - vector.y)
    
    open operator fun unaryMinus(): BetterVector =
            BetterVector(- this.x, -this.y)
}