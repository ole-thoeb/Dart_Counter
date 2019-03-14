package com.example.eloem.dartCounter.helperClasses

import android.content.Context
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.example.eloem.dartCounter.R
import com.example.eloem.dartCounter.games.Point
import com.example.eloem.dartCounter.util.getAttribute
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DartBoard @JvmOverloads constructor(context: Context,
                                          attrs: AttributeSet? = null,
                                          defStyleAttr: Int = 0,
                                          defStyleRes: Int = 0):
        ImageView(context, attrs, defStyleAttr, defStyleRes){
    
    private var needToUpdateMappingPoints = true
    private lateinit var mappingPoints: MutableList<BetterPoint>
    //starts at 3 and goes around counterclockwise
    private val mappingList = listOf(3, 17, 2, 15, 10, 6, 13, 4, 18, 1, 20, 5, 12, 9, 14, 11, 8, 16, 7, 19)
    
    private val backgroundDrawable = DartboardDrawable(context.getAttribute(R.attr.colorSecondary).data,
            context.getAttribute(R.attr.colorPrimary).data)
    
    var onPointListener: ((Point) -> Unit)? = null
    var onNextPointListener: (() -> Unit)? = null
    
    init {
        //setImageResource(R.mipmap.dartscheibe)
        scaleType = ScaleType.FIT_XY
        adjustViewBounds = true
        setImageDrawable(backgroundDrawable)
        isClickable = true
        
        setOnTouchListener{ view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val circlePercent = 0.33
            
                val touchPoint = BetterPoint(motionEvent.x.toDouble(), motionEvent.y.toDouble())
            
                val width = view.width
                val height = view.height
            
                val radBoard = if (width < height) width / 2
                else height / 2
            
                val center = BetterPoint((width / 2).toDouble(), (height / 2).toDouble())
            
                val radCircle = radBoard * circlePercent
            
                val dist = touchPoint.distance(center)
            
                if (needToUpdateMappingPoints) {
                    mappingPoints = createPoints(center, radBoard)
                    needToUpdateMappingPoints = false
                }
            
                when {
                    (dist < radCircle) -> setThrowPoints(Point.instanceByPoints(1, 25), 2)
                    (dist > radBoard) -> setThrowPoints(Point.instanceByPoints(1, 0), 1)
                    else -> {
                        val shortestPoint = findNearest(mappingPoints, touchPoint)
                        setThrowPoints(Point.instanceByPoints(1, mappingList[shortestPoint]), 3)
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
    
    private var lastTouch = System.currentTimeMillis()
    private var lastPoint = Point.instanceByPoints(1, 0)
    private var pressesToWait = 0
    
    private fun setThrowPoints(point: Point, limit: Int) {
        val currentMillis = System.currentTimeMillis()
        val timespan = 300L
        
        val multiplicator = if (lastTouch > currentMillis - timespan
                && lastPoint.point == point.point
                && lastPoint.multiplicator < limit) {
            pressesToWait++
            
            lastPoint.multiplicator + 1
        } else {
            
            if (lastTouch > currentMillis - timespan){
                pressesToWait ++
                //still have to reset the last sector that was pressed
                backgroundDrawable.setState(lastPoint.point, DartboardDrawable.STATE_NOT_PRESSED)
            }
            
            point.multiplicator
        }
        
        val finalPoint = Point.instanceByPoints(multiplicator, point.point)
        
        backgroundDrawable.setState(finalPoint.point, finalPoint.multiplicator)
        onPointListener?.invoke(finalPoint)
        
        lastPoint = finalPoint.copy()
        
        doAsync {
            if (limit > 1) Thread.sleep(timespan)
            
            uiThread {
                if (pressesToWait > 0) {
                    pressesToWait --
                } else {
                    backgroundDrawable.setState(finalPoint.point, DartboardDrawable.STATE_NOT_PRESSED)
                    onNextPointListener?.invoke()
                }
            }
        }
        
        lastTouch = currentMillis
    }
}