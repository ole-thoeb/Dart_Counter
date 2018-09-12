package com.example.eloem.dartCounter.helperClasses

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.example.eloem.dartCounter.R
import com.example.eloem.dartCounter.helperClasses.games.DartGame
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DartBoard: ImageView{
    
    private var needToUpdateMappingPoints = true
    private lateinit var mappingPoints: MutableList<BetterPoint>
    //starts at 3 and goes around counterclockwise
    private val mappingList = arrayOf(3, 17, 2, 15, 10, 6, 13, 4, 18, 1, 20, 5, 12, 9, 14, 11, 8, 16, 7, 19)
    
    private var lastTouch = System.currentTimeMillis()
    private var lastPoint = DartGame.Point.instanceByPoints(1, 0)
    private var switchesToStop = 0
    
    var onPointListener: (DartGame.Point) -> Unit = {_ -> Unit}
    var onNextPointListener: () -> Unit = {}
    
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)
    
    init {
        setImageResource(R.mipmap.dartscheibe)
        scaleType = ScaleType.FIT_XY
        adjustViewBounds = true
        isClickable = true
        
        setOnTouchListener{ view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val firstCirclePercent = 0.19157
                val secondCirclePercent = 0.370096
            
                val touchPoint = BetterPoint(motionEvent.x.toDouble(), motionEvent.y.toDouble())
            
                val width = view.width
                val height = view.height
            
                val radBoard: Int
                radBoard = if (width < height) width / 2
                else height / 2
            
                val center = BetterPoint((width / 2).toDouble(), (height / 2).toDouble())
            
                val radFirstCircle = radBoard * firstCirclePercent
                val radSecondCircle = radBoard * secondCirclePercent
            
                val dist = touchPoint.distance(center)
            
                if (needToUpdateMappingPoints) {
                    mappingPoints = createPoints(center, radBoard)
                    needToUpdateMappingPoints = false
                }
            
                when {
                    (dist < radFirstCircle) -> setThrowPoints(DartGame.Point.instanceByPoints(2, 25), false)
                    (dist < radSecondCircle) -> setThrowPoints(DartGame.Point.instanceByPoints(1, 25), false)
                    (dist > radBoard) -> setThrowPoints(DartGame.Point.instanceByPoints(1, 0), false)
                    else -> {
                        val shortestPoint = findNearest(mappingPoints, touchPoint)
                        setThrowPoints(DartGame.Point.instanceByPoints(1, mappingList[shortestPoint]), true)
                    }
                }
            }
        
            true
        }
    }
    
    private var scrollListener: ViewTreeObserver.OnScrollChangedListener? = null
    private var sV: NestedScrollView? = null
    
    fun setScrollObserver(scrollView: NestedScrollView){
        if (scrollListener != null){
            removeScrollObserver()
        }
        sV = scrollView
    
        //wenn sich die position der Darscheibe ändert müssen die Punkte neu berechnet werden
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            needToUpdateMappingPoints = true
        }
    }
    
    fun removeScrollObserver(){
        sV?.viewTreeObserver?.removeOnScrollChangedListener(scrollListener)
    }
    
    private fun createPoints(center: BetterPoint, radBoard: Int): MutableList<BetterPoint> {
        val pointList = ArrayList<BetterPoint>()
        
        //val ratioToOutside = 0.31286
        
        var vectorToPoint = BetterVector(0.0, radBoard.toDouble())
        
        for (i in 1..20) {
            pointList.add(center + vectorToPoint)
            vectorToPoint = vectorToPoint.rotate(18.0)
        }
        
        return pointList
    }
    
    private fun findNearest(pointList: MutableList<BetterPoint>, point: BetterPoint): Int {
        var shortestDistance = pointList[0].distance(point)
        var shortestPos = 0
        
        for ((index, lPoint) in pointList.withIndex()) {
            val dist = lPoint.distance(point)
            
            if (dist < shortestDistance) {
                shortestDistance = dist
                shortestPos = index
            }
        }
        
        return shortestPos
    }
    
    private fun setThrowPoints(mPoints: DartGame.Point, multiple: Boolean) {
        val currentMillis = System.currentTimeMillis()
        val timespan = 300L
        
        val multiplicator = if (lastTouch > currentMillis - timespan
                && lastPoint.point == mPoints.point
                && multiple
                && lastPoint.multiplicator < 3) {
            switchesToStop++
            
            lastPoint.multiplicator + 1
        } else {
            
            if (lastTouch > currentMillis - timespan) switchesToStop ++
            
            mPoints.multiplicator
        }
        
        val finalPoint = DartGame.Point.instanceByPoints(multiplicator, mPoints.point)
        
        onPointListener(finalPoint)
        
        lastPoint = finalPoint.copy()
        
        doAsync {
            if (multiple) Thread.sleep(timespan)
            
            uiThread {
                if (switchesToStop > 0) switchesToStop --
                else onNextPointListener()
            }
        }
        
        lastTouch = currentMillis
    }
}