package com.example.eloem.dartCounter.helperClasses

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log

class DartboardDrawable: Drawable() {
    private lateinit var path20: Path
    private lateinit var path1: Path
    private lateinit var path18: Path
    private lateinit var path4: Path
    private lateinit var path13: Path
    private lateinit var path6: Path
    private lateinit var path10: Path
    private lateinit var path15: Path
    private lateinit var path2: Path
    private lateinit var path17: Path
    private lateinit var path3: Path
    private lateinit var path19: Path
    private lateinit var path7: Path
    private lateinit var path16: Path
    private lateinit var path8: Path
    private lateinit var path11: Path
    private lateinit var path14: Path
    private lateinit var path9: Path
    private lateinit var path12: Path
    private lateinit var path5: Path
    
    private val paint1 = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        isAntiAlias = true
    }
    
    private val paint2 = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }
    
    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        init()
    }
    
    private var scaleFactor = 0f
    
    private fun scale(f: Float) = f * scaleFactor
    
    private fun init(){
        val width = bounds.width()
        Log.d(TAG, "width: $width")
        scaleFactor = width / 158f
        
        path20 = Path().apply {
            moveTo(scale(67.379f), scale(3.635f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(91.363f), scale(3.683f))
            cubicTo(scale(87.401f), scale(3.024f), scale(83.392f), scale(2.677f), scale(79.375f), scale(2.646f))
            cubicTo(scale(75.357f), scale(2.661f), scale(71.346f), scale(2.992f), scale(67.379f), scale(3.635f))
            close()
        }
        path1 = Path().apply {
            moveTo(scale(91.36f), scale(3.68f))
            lineTo(scale(79.38f), scale(79.38f))
            lineTo(scale(114.17f), scale(11.08f))
            cubicTo(scale(107.01f), scale(7.43f), scale(99.31f), scale(4.94f), scale(91.36f), scale(3.68f))
            close()
        }
        path18 = Path().apply {
            moveTo(scale(114.174f), scale(11.079f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(133.597f), scale(25.153f))
            cubicTo(scale(127.901f), scale(19.473f), scale(121.346f), scale(14.723f), scale(114.174f), scale(11.079f))
            close()
        }
        path4 = Path().apply {
            moveTo(scale(133.597f), scale(25.153f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(147.739f), scale(44.542f))
            cubicTo(scale(144.071f), scale(37.377f), scale(139.299f), scale(30.834f), scale(133.597f), scale(25.153f))
            close()
        }
        path13 = Path().apply {
            moveTo(scale(147.739f), scale(44.542f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(155.115f), scale(67.38f))
            cubicTo(scale(153.87f), scale(59.427f), scale(151.381f), scale(51.72f), scale(147.739f), scale(44.542f))
            close()
        }
        path6 = Path().apply {
            moveTo(scale(155.115f), scale(67.38f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(155.066f), scale(91.364f))
            cubicTo(scale(155.726f), scale(87.401f), scale(156.073f), scale(83.392f), scale(156.104f), scale(79.375f))
            cubicTo(scale(156.089f), scale(75.357f), scale(155.758f), scale(71.346f), scale(155.115f), scale(67.38f))
            close()
        }
        path10 = Path().apply {
            moveTo(scale(155.066f), scale(91.364f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(147.671f), scale(114.174f))
            cubicTo(scale(151.316f), scale(107.005f), scale(153.812f), scale(99.307f), scale(155.066f), scale(91.364f))
            close()
        }
        path15 = Path().apply {
            moveTo(scale(147.671f), scale(114.174f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(133.597f), scale(133.597f))
            cubicTo(scale(139.278f), scale(127.9f), scale(144.027f), scale(121.346f), scale(147.671f), scale(114.174f))
            close()
        }
        path2 = Path().apply {
            moveTo(scale(133.597f), scale(133.597f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(114.209f), scale(147.739f))
            cubicTo(scale(121.373f), scale(144.071f), scale(127.916f), scale(139.299f), scale(133.597f), scale(133.597f))
            close()
        }
        path17 = Path().apply {
            moveTo(scale(114.209f), scale(147.739f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(91.371f), scale(155.115f))
            cubicTo(scale(99.323f), scale(153.87f), scale(107.03f), scale(151.381f), scale(114.209f), scale(147.739f))
            close()
        }
        path3 = Path().apply {
            moveTo(scale(91.371f), scale(155.115f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(67.387f), scale(155.067f))
            cubicTo(scale(71.349f), scale(155.726f), scale(75.358f), scale(156.073f), scale(79.375f), scale(156.104f))
            cubicTo(scale(83.393f), scale(156.089f), scale(87.404f), scale(155.758f), scale(91.371f), scale(155.115f))
            close()
        }
        path19 = Path().apply {
            moveTo(scale(67.387f), scale(155.067f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(44.577f), scale(147.671f))
            cubicTo(scale(51.745f), scale(151.316f), scale(59.443f), scale(153.812f), scale(67.387f), scale(155.067f))
            close()
        }
        path7 = Path().apply {
            moveTo(scale(44.577f), scale(147.671f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(25.153f), scale(133.597f))
            cubicTo(scale(30.85f), scale(139.278f), scale(37.404f), scale(144.027f), scale(44.577f), scale(147.671f))
            close()
        }
        path16 = Path().apply {
            moveTo(scale(25.153f), scale(133.597f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(11.011f), scale(114.209f))
            cubicTo(scale(14.679f), scale(121.373f), scale(19.451f), scale(127.916f), scale(25.153f), scale(133.597f))
            close()
        }
        path8 = Path().apply {
            moveTo(scale(11.011f), scale(114.209f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(3.635f), scale(91.371f))
            cubicTo(scale(4.88f), scale(99.323f), scale(7.369f), scale(107.03f), scale(11.011f), scale(114.209f))
            close()
        }
        path11 = Path().apply {
            moveTo(scale(3.635f), scale(91.371f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(3.683f), scale(67.387f))
            cubicTo(scale(3.024f), scale(71.35f), scale(2.677f), scale(75.358f), scale(2.646f), scale(79.375f))
            cubicTo(scale(2.661f), scale(83.394f), scale(2.992f), scale(87.404f), scale(3.635f), scale(91.371f))
            close()
        }
        path14 = Path().apply {
            moveTo(scale(3.683f), scale(67.387f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(11.079f), scale(44.577f))
            cubicTo(scale(7.434f), scale(51.746f), scale(4.938f), scale(59.443f), scale(3.683f), scale(67.387f))
            close()
        }
        path9 = Path().apply {
            moveTo(scale(11.079f), scale(44.577f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(25.153f), scale(25.153f))
            cubicTo(scale(19.472f), scale(30.85f), scale(14.723f), scale(37.405f), scale(11.079f), scale(44.577f))
            close()
        }
        path12 = Path().apply {
            moveTo(scale(25.153f), scale(25.153f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(44.541f), scale(11.011f))
            cubicTo(scale(37.377f), scale(14.679f), scale(30.834f), scale(19.451f), scale(25.153f), scale(25.153f))
            close()
        }
        path5 = Path().apply {
            moveTo(scale(67.379f), scale(3.635f))
            cubicTo(scale(59.427f), scale(4.881f), scale(51.72f), scale(7.37f), scale(44.541f), scale(11.011f))
            lineTo(scale(79.375f), scale(79.375f))
            close()
        }
    }
    
    override fun draw(canvas: Canvas) {
        canvas.apply {
            drawPath(path20, paint1)
            drawPath(path1, paint2)
            drawPath(path18, paint1)
            drawPath(path4, paint2)
            drawPath(path13, paint1)
            drawPath(path6, paint2)
            drawPath(path10, paint1)
            drawPath(path15, paint2)
            drawPath(path2, paint1)
            drawPath(path17, paint2)
            drawPath(path3, paint1)
            drawPath(path19, paint2)
            drawPath(path7, paint1)
            drawPath(path16, paint2)
            drawPath(path8, paint1)
            drawPath(path11, paint2)
            drawPath(path14, paint1)
            drawPath(path9, paint2)
            drawPath(path12, paint1)
            drawPath(path5, paint2)
        }
    }
    
    override fun setAlpha(alpha: Int) {
    }
    
    override fun getOpacity(): Int = PixelFormat.UNKNOWN
    
    override fun setColorFilter(colorFilter: ColorFilter?) {
    }
    
    companion object {
        private const val TAG = "DartBoardDrawable"
    }
}