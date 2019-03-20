package com.example.eloem.dartCounter.helperClasses

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.eloem.dartCounter.util.differentShade
import com.example.eloem.dartCounter.util.dp

class DartboardDrawable(val c1: Int = Color.RED, val c2: Int = Color.GREEN): Drawable() {
    
    private data class DetailedPath(val path: Path, var state: Int, val colors: Array<Paint>){
        val paint get() = colors[state]
    }
    
    private var path20: Path = Path()
    private var path1: Path = Path()
    private var path18: Path = Path()
    private var path4: Path = Path()
    private var path13: Path = Path()
    private var path6: Path = Path()
    private var path10: Path = Path()
    private var path15: Path = Path()
    private var path2: Path = Path()
    private var path17: Path = Path()
    private var path3: Path = Path()
    private var path19: Path = Path()
    private var path7: Path = Path()
    private var path16: Path = Path()
    private var path8: Path = Path()
    private var path11: Path = Path()
    private var path14: Path = Path()
    private var path9: Path = Path()
    private var path12: Path = Path()
    private var path5: Path = Path()
    private var path25: Path = Path()
    
    private var outerPath: Path = Path()
    
    //private var pathNumber20: Path = Path()
    //private var pathNumber1: Path = Path()
    //private var pathNumber18: Path = Path()
    //private var pathNumber4: Path = Path()
    //private var pathNumber13: Path = Path()
    //private var pathNumber6: Path = Path()
    //private var pathNumber10: Path = Path()
    //private var pathNumber15: Path = Path()
    //private var pathNumber2: Path = Path()
    //private var pathNumber17: Path = Path()
    //private var pathNumber3: Path = Path()
    //private var pathNumber19: Path = Path()
    //private var pathNumber7: Path = Path()
    //private var pathNumber16: Path = Path()
    //private var pathNumber8: Path = Path()
    //private var pathNumber11: Path = Path()
    //private var pathNumber14: Path = Path()
    //private var pathNumber9: Path = Path()
    //private var pathNumber12: Path = Path()
    //private var pathNumber5: Path = Path()
    
    private val paint1_0 = Paint().apply {
        style = Paint.Style.FILL
        color = c1
        isAntiAlias = true
    }
    
    private val paint1_1 = Paint().apply {
        style = Paint.Style.FILL
        color = differentShade(c1, -0.05f)
        isAntiAlias = true
    }
    
    private val paint1_2 = Paint().apply {
        style = Paint.Style.FILL
        color = differentShade(c1, -0.1f)
        isAntiAlias = true
    }
    
    private val paint1_3 = Paint().apply {
        style = Paint.Style.FILL
        color = differentShade(c1, -0.15f)
        isAntiAlias = true
    }
    
    private val paint2_0 = Paint().apply {
        style = Paint.Style.FILL
        color = c2
        isAntiAlias = true
    }
    
    private val paint2_1 = Paint().apply {
        style = Paint.Style.FILL
        color = differentShade(c2, -0.05f)
        isAntiAlias = true
    }
    
    private val paint2_2 = Paint().apply {
        style = Paint.Style.FILL
        color = differentShade(c2, -0.1f)
        isAntiAlias = true
    }
    
    private val paint2_3 = Paint().apply {
        style = Paint.Style.FILL
        color = differentShade(c2, -0.15f)
        isAntiAlias = true
    }
    
    private val boarderPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        isAntiAlias = true
    }
    
    private val numberPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        isAntiAlias = true
    }
    
    private fun default1(path: Path) = DetailedPath(path, STATE_NOT_PRESSED, arrayOf(paint1_0, paint1_1, paint1_2, paint1_3))
    
    private fun default2(path: Path) = DetailedPath(path, STATE_NOT_PRESSED, arrayOf(paint2_0, paint2_1, paint2_2, paint2_3))
    
    private val paths = arrayOf(default1(path1), default2(path2), default2(path3), default1(path4),
            default1(path5), default1(path6), default2(path7), default2(path8), default1(path9),
            default2(path10), default1(path11), default2(path12), default2(path13), default2(path14),
            default1(path15), default1(path16), default1(path17), default2(path18), default1(path19),
            default2(path20), default2(path25))
    
    fun setState(segment: Int, newState: Int) {
        if (segment == 0) return
        
        val internalSegment = if (segment == 25) 20 else segment - 1
        
        require(internalSegment in 0..20)
        require(newState in 0..3)
        
        paths[internalSegment].state = newState
        invalidateSelf()
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
        
        //set stroke width
        boarderPaint.strokeWidth = scale(1f)
    
        numberPaint.textSize = scale(9f)
        
        path20.apply {
            reset()
            moveTo(scale(67.379f), scale(3.635f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(91.363f), scale(3.683f))
            cubicTo(scale(87.401f), scale(3.024f), scale(83.392f), scale(2.677f), scale(79.375f), scale(2.646f))
            cubicTo(scale(75.357f), scale(2.661f), scale(71.346f), scale(2.992f), scale(67.379f), scale(3.635f))
            close()
        }
        path1.apply {
            reset()
            moveTo(scale(91.36f), scale(3.68f))
            lineTo(scale(79.38f), scale(79.38f))
            lineTo(scale(114.17f), scale(11.08f))
            cubicTo(scale(107.01f), scale(7.43f), scale(99.31f), scale(4.94f), scale(91.36f), scale(3.68f))
            close()
        }
        path18.apply {
            reset()
            moveTo(scale(114.174f), scale(11.079f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(133.597f), scale(25.153f))
            cubicTo(scale(127.901f), scale(19.473f), scale(121.346f), scale(14.723f), scale(114.174f), scale(11.079f))
            close()
        }
        path4.apply {
            reset()
            moveTo(scale(133.597f), scale(25.153f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(147.739f), scale(44.542f))
            cubicTo(scale(144.071f), scale(37.377f), scale(139.299f), scale(30.834f), scale(133.597f), scale(25.153f))
            close()
        }
        path13.apply {
            reset()
            moveTo(scale(147.739f), scale(44.542f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(155.115f), scale(67.38f))
            cubicTo(scale(153.87f), scale(59.427f), scale(151.381f), scale(51.72f), scale(147.739f), scale(44.542f))
            close()
        }
        path6.apply {
            reset()
            moveTo(scale(155.115f), scale(67.38f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(155.066f), scale(91.364f))
            cubicTo(scale(155.726f), scale(87.401f), scale(156.073f), scale(83.392f), scale(156.104f), scale(79.375f))
            cubicTo(scale(156.089f), scale(75.357f), scale(155.758f), scale(71.346f), scale(155.115f), scale(67.38f))
            close()
        }
        path10.apply {
            reset()
            moveTo(scale(155.066f), scale(91.364f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(147.671f), scale(114.174f))
            cubicTo(scale(151.316f), scale(107.005f), scale(153.812f), scale(99.307f), scale(155.066f), scale(91.364f))
            close()
        }
        path15.apply {
            reset()
            moveTo(scale(147.671f), scale(114.174f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(133.597f), scale(133.597f))
            cubicTo(scale(139.278f), scale(127.9f), scale(144.027f), scale(121.346f), scale(147.671f), scale(114.174f))
            close()
        }
        path2.apply {
            reset()
            moveTo(scale(133.597f), scale(133.597f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(114.209f), scale(147.739f))
            cubicTo(scale(121.373f), scale(144.071f), scale(127.916f), scale(139.299f), scale(133.597f), scale(133.597f))
            close()
        }
        path17.apply {
            reset()
            moveTo(scale(114.209f), scale(147.739f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(91.371f), scale(155.115f))
            cubicTo(scale(99.323f), scale(153.87f), scale(107.03f), scale(151.381f), scale(114.209f), scale(147.739f))
            close()
        }
        path3.apply {
            reset()
            moveTo(scale(91.371f), scale(155.115f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(67.387f), scale(155.067f))
            cubicTo(scale(71.349f), scale(155.726f), scale(75.358f), scale(156.073f), scale(79.375f), scale(156.104f))
            cubicTo(scale(83.393f), scale(156.089f), scale(87.404f), scale(155.758f), scale(91.371f), scale(155.115f))
            close()
        }
        path19.apply {
            reset()
            moveTo(scale(67.387f), scale(155.067f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(44.577f), scale(147.671f))
            cubicTo(scale(51.745f), scale(151.316f), scale(59.443f), scale(153.812f), scale(67.387f), scale(155.067f))
            close()
        }
        path7.apply {
            reset()
            moveTo(scale(44.577f), scale(147.671f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(25.153f), scale(133.597f))
            cubicTo(scale(30.85f), scale(139.278f), scale(37.404f), scale(144.027f), scale(44.577f), scale(147.671f))
            close()
        }
        path16.apply {
            reset()
            moveTo(scale(25.153f), scale(133.597f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(11.011f), scale(114.209f))
            cubicTo(scale(14.679f), scale(121.373f), scale(19.451f), scale(127.916f), scale(25.153f), scale(133.597f))
            close()
        }
        path8.apply {
            reset()
            moveTo(scale(11.011f), scale(114.209f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(3.635f), scale(91.371f))
            cubicTo(scale(4.88f), scale(99.323f), scale(7.369f), scale(107.03f), scale(11.011f), scale(114.209f))
            close()
        }
        path11.apply {
            reset()
            moveTo(scale(3.635f), scale(91.371f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(3.683f), scale(67.387f))
            cubicTo(scale(3.024f), scale(71.35f), scale(2.677f), scale(75.358f), scale(2.646f), scale(79.375f))
            cubicTo(scale(2.661f), scale(83.394f), scale(2.992f), scale(87.404f), scale(3.635f), scale(91.371f))
            close()
        }
        path14.apply {
            reset()
            moveTo(scale(3.683f), scale(67.387f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(11.079f), scale(44.577f))
            cubicTo(scale(7.434f), scale(51.746f), scale(4.938f), scale(59.443f), scale(3.683f), scale(67.387f))
            close()
        }
        path9.apply {
            reset()
            moveTo(scale(11.079f), scale(44.577f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(25.153f), scale(25.153f))
            cubicTo(scale(19.472f), scale(30.85f), scale(14.723f), scale(37.405f), scale(11.079f), scale(44.577f))
            close()
        }
        path12.apply {
            reset()
            moveTo(scale(25.153f), scale(25.153f))
            lineTo(scale(79.375f), scale(79.375f))
            lineTo(scale(44.541f), scale(11.011f))
            cubicTo(scale(37.377f), scale(14.679f), scale(30.834f), scale(19.451f), scale(25.153f), scale(25.153f))
            close()
        }
        path5.apply {
            reset()
            moveTo(scale(67.379f), scale(3.635f))
            cubicTo(scale(59.427f), scale(4.881f), scale(51.72f), scale(7.37f), scale(44.541f), scale(11.011f))
            lineTo(scale(79.375f), scale(79.375f))
            close()
        }
        path25.apply {
            reset()
            addCircle(scale(79f), scale(79f), scale(25f), Path.Direction.CW)
        }
        
        outerPath.apply {
            reset()
            addCircle(scale(79.375f), scale(79.375f), scale(76.5f), Path.Direction.CW)
        }
        
        /*pathNumber20.apply {
            reset()
            moveTo(scale(78.889f), scale(12.95f))
            lineTo(scale(78.889f), scale(14.023f))
            lineTo(scale(72.88f), scale(14.023f))
            cubicTo(scale(72.884f), scale(12.867f), scale(73.751f), scale(11.953f), scale(74.552f), scale(11.222f))
            cubicTo(scale(75.569f), scale(10.296f), scale(76.782f), scale(9.516f), scale(77.483f), scale(8.302f))
            cubicTo(scale(77.889f), scale(7.624f), scale(77.797f), scale(6.65f), scale(77.13f), scale(6.17f))
            cubicTo(scale(76.265f), scale(5.505f), scale(74.732f), scale(5.781f), scale(74.364f), scale(6.879f))
            cubicTo(scale(74.195f), scale(7.2f), scale(74.408f), scale(7.811f), scale(73.886f), scale(7.606f))
            lineTo(scale(73.091f), scale(7.524f))
            cubicTo(scale(73.145f), scale(6.517f), scale(73.711f), scale(5.497f), scale(74.69f), scale(5.133f))
            cubicTo(scale(75.689f), scale(4.768f), scale(76.897f), scale(4.783f), scale(77.809f), scale(5.374f))
            cubicTo(scale(78.819f), scale(6.023f), scale(79.142f), scale(7.422f), scale(78.654f), scale(8.491f))
            cubicTo(scale(78.104f), scale(9.67f), scale(77.018f), scale(10.463f), scale(76.069f), scale(11.305f))
            cubicTo(scale(75.488f), scale(11.815f), scale(74.845f), scale(12.284f), scale(74.431f), scale(12.95f))
            lineTo(scale(78.889f), scale(12.95f))
            close()
            moveTo(scale(80.092f), scale(9.539f))
            cubicTo(scale(80.104f), scale(8.158f), scale(80.216f), scale(6.604f), scale(81.249f), scale(5.568f))
            cubicTo(scale(82.335f), scale(4.528f), scale(84.371f), scale(4.69f), scale(85.206f), scale(5.973f))
            cubicTo(scale(86.08f), scale(7.295f), scale(86.06f), scale(8.957f), scale(85.988f), scale(10.479f))
            cubicTo(scale(85.907f), scale(11.719f), scale(85.569f), scale(13.12f), scale(84.45f), scale(13.826f))
            cubicTo(scale(83.305f), scale(14.482f), scale(81.625f), scale(14.239f), scale(80.883f), scale(13.088f))
            cubicTo(scale(80.2f), scale(12.05f), scale(80.106f), scale(10.748f), scale(80.092f), scale(9.539f))
            close()
            moveTo(scale(81.239f), scale(9.539f))
            cubicTo(scale(81.263f), scale(10.509f), scale(81.248f), scale(11.538f), scale(81.697f), scale(12.423f))
            cubicTo(scale(82.077f), scale(13.14f), scale(83.044f), scale(13.512f), scale(83.763f), scale(13.074f))
            cubicTo(scale(84.634f), scale(12.565f), scale(84.773f), scale(11.474f), scale(84.84f), scale(10.563f))
            cubicTo(scale(84.889f), scale(9.336f), scale(84.945f), scale(8.059f), scale(84.525f), scale(6.886f))
            cubicTo(scale(84.232f), scale(6.107f), scale(83.314f), scale(5.587f), scale(82.511f), scale(5.91f))
            cubicTo(scale(81.655f), scale(6.243f), scale(81.423f), scale(7.243f), scale(81.321f), scale(8.058f))
            cubicTo(scale(81.259f), scale(8.549f), scale(81.24f), scale(9.045f), scale(81.239f), scale(9.539f))
            close()
        }
        pathNumber1.apply {
            reset()
            moveTo(scale(101.304f), scale(17.462f))
            lineTo(scale(100.188f), scale(17.462f))
            lineTo(scale(100.188f), scale(10.35f))
            cubicTo(scale(99.559f), scale(10.922f), scale(98.803f), scale(11.344f), scale(98.021f), scale(11.671f))
            cubicTo(scale(97.891f), scale(11.72f), scale(97.984f), scale(11.445f), scale(97.956f), scale(11.345f))
            lineTo(scale(97.956f), scale(10.616f))
            cubicTo(scale(98.962f), scale(10.136f), scale(99.912f), scale(9.442f), scale(100.506f), scale(8.482f))
            cubicTo(scale(100.538f), scale(8.278f), scale(100.728f), scale(8.343f), scale(100.877f), scale(8.334f))
            lineTo(scale(101.304f), scale(8.334f))
            lineTo(scale(101.304f), scale(17.462f))
            close()
        }
        pathNumber18.apply {
            reset()
            moveTo(scale(116.535f), scale(29.025f))
            lineTo(scale(115.418f), scale(29.025f))
            lineTo(scale(115.418f), scale(21.913f))
            cubicTo(scale(114.771f), scale(22.499f), scale(113.993f), scale(22.934f), scale(113.186f), scale(23.258f))
            lineTo(scale(113.186f), scale(22.179f))
            cubicTo(scale(114.24f), scale(21.674f), scale(115.246f), scale(20.939f), scale(115.815f), scale(19.897f))
            lineTo(scale(116.535f), scale(19.897f))
            lineTo(scale(116.535f), scale(29.025f))
            close()
            moveTo(scale(121.117f), scale(24.095f))
            cubicTo(scale(120.376f), scale(23.868f), scale(119.774f), scale(23.197f), scale(119.758f), scale(22.403f))
            cubicTo(scale(119.635f), scale(21.245f), scale(120.541f), scale(20.152f), scale(121.669f), scale(19.963f))
            cubicTo(scale(122.724f), scale(19.745f), scale(123.964f), scale(20.021f), scale(124.597f), scale(20.955f))
            cubicTo(scale(125.234f), scale(21.814f), scale(125.152f), scale(23.239f), scale(124.191f), scale(23.833f))
            cubicTo(scale(123.975f), scale(24.007f), scale(123.41f), scale(24.097f), scale(123.966f), scale(24.21f))
            cubicTo(scale(124.949f), scale(24.593f), scale(125.501f), scale(25.689f), scale(125.365f), scale(26.713f))
            cubicTo(scale(125.279f), scale(27.999f), scale(124.134f), scale(29.052f), scale(122.869f), scale(29.152f))
            cubicTo(scale(121.773f), scale(29.3f), scale(120.544f), scale(28.96f), scale(119.89f), scale(28.018f))
            cubicTo(scale(119.201f), scale(27.06f), scale(119.179f), scale(25.577f), scale(120.017f), scale(24.702f))
            cubicTo(scale(120.315f), scale(24.398f), scale(120.708f), scale(24.201f), scale(121.117f), scale(24.095f))
            close()
            moveTo(scale(120.894f), scale(22.204f))
            cubicTo(scale(120.846f), scale(22.982f), scale(121.53f), scale(23.682f), scale(122.306f), scale(23.666f))
            cubicTo(scale(123.102f), scale(23.754f), scale(123.893f), scale(23.081f), scale(123.858f), scale(22.266f))
            cubicTo(scale(123.898f), scale(21.383f), scale(122.998f), scale(20.665f), scale(122.144f), scale(20.829f))
            cubicTo(scale(121.466f), scale(20.9f), scale(120.876f), scale(21.511f), scale(120.894f), scale(22.204f))
            close()
            moveTo(scale(120.534f), scale(26.402f))
            cubicTo(scale(120.502f), scale(27.272f), scale(121.15f), scale(28.103f), scale(122.018f), scale(28.229f))
            cubicTo(scale(122.91f), scale(28.425f), scale(123.927f), scale(27.905f), scale(124.163f), scale(26.996f))
            cubicTo(scale(124.438f), scale(26.105f), scale(123.989f), scale(25.04f), scale(123.095f), scale(24.711f))
            cubicTo(scale(122.222f), scale(24.355f), scale(121.081f), scale(24.707f), scale(120.697f), scale(25.606f))
            cubicTo(scale(120.583f), scale(25.854f), scale(120.534f), scale(26.13f), scale(120.534f), scale(26.402f))
            close()
        }
        pathNumber4.apply {
            reset()
            moveTo(scale(135.974f), scale(42.83f))
            lineTo(scale(135.974f), scale(40.653f))
            lineTo(scale(132.03f), scale(40.653f))
            lineTo(scale(132.03f), scale(39.63f))
            cubicTo(scale(133.413f), scale(37.666f), scale(134.796f), scale(35.702f), scale(136.179f), scale(33.739f))
            lineTo(scale(137.091f), scale(33.739f))
            lineTo(scale(137.091f), scale(39.63f))
            lineTo(scale(138.318f), scale(39.63f))
            lineTo(scale(138.318f), scale(40.653f))
            lineTo(scale(137.091f), scale(40.653f))
            lineTo(scale(137.091f), scale(42.83f))
            lineTo(scale(135.974f), scale(42.83f))
            close()
            moveTo(scale(135.974f), scale(39.63f))
            lineTo(scale(135.974f), scale(35.531f))
            lineTo(scale(133.128f), scale(39.63f))
            lineTo(scale(135.974f), scale(39.63f))
            close()
        }
        pathNumber13.apply {
            reset()
            moveTo(scale(140.685f), scale(63.446f))
            lineTo(scale(139.569f), scale(63.446f))
            lineTo(scale(139.569f), scale(56.334f))
            cubicTo(scale(138.922f), scale(56.92f), scale(138.144f), scale(57.355f), scale(137.336f), scale(57.679f))
            lineTo(scale(137.336f), scale(56.6f))
            cubicTo(scale(138.39f), scale(56.095f), scale(139.396f), scale(55.36f), scale(139.966f), scale(54.318f))
            lineTo(scale(140.685f), scale(54.318f))
            lineTo(scale(140.685f), scale(63.446f))
            close()
            moveTo(scale(143.556f), scale(61.046f))
            cubicTo(scale(143.928f), scale(60.997f), scale(144.3f), scale(60.947f), scale(144.672f), scale(60.898f))
            cubicTo(scale(144.795f), scale(61.632f), scale(145.194f), scale(62.443f), scale(145.982f), scale(62.625f))
            cubicTo(scale(146.986f), scale(62.919f), scale(148.126f), scale(62.208f), scale(148.291f), scale(61.173f))
            cubicTo(scale(148.492f), scale(60.302f), scale(147.975f), scale(59.314f), scale(147.088f), scale(59.089f))
            cubicTo(scale(146.69f), scale(58.874f), scale(145.991f), scale(59.192f), scale(145.769f), scale(59.052f))
            cubicTo(scale(145.807f), scale(58.755f), scale(145.844f), scale(58.459f), scale(145.882f), scale(58.163f))
            cubicTo(scale(146.626f), scale(58.225f), scale(147.553f), scale(57.942f), scale(147.835f), scale(57.181f))
            cubicTo(scale(148.107f), scale(56.452f), scale(147.728f), scale(55.532f), scale(146.956f), scale(55.313f))
            cubicTo(scale(146.208f), scale(55.061f), scale(145.284f), scale(55.398f), scale(144.986f), scale(56.157f))
            cubicTo(scale(144.804f), scale(56.448f), scale(144.927f), scale(57.045f), scale(144.452f), scale(56.814f))
            cubicTo(scale(144.191f), scale(56.768f), scale(143.929f), scale(56.721f), scale(143.668f), scale(56.675f))
            cubicTo(scale(143.824f), scale(55.58f), scale(144.64f), scale(54.567f), scale(145.763f), scale(54.379f))
            cubicTo(scale(146.973f), scale(54.12f), scale(148.44f), scale(54.642f), scale(148.905f), scale(55.86f))
            cubicTo(scale(149.292f), scale(56.748f), scale(148.961f), scale(57.889f), scale(148.101f), scale(58.368f))
            cubicTo(scale(147.834f), scale(58.491f), scale(147.756f), scale(58.548f), scale(148.1f), scale(58.607f))
            cubicTo(scale(149.059f), scale(58.905f), scale(149.597f), scale(59.944f), scale(149.506f), scale(60.909f))
            cubicTo(scale(149.478f), scale(62.343f), scale(148.176f), scale(63.534f), scale(146.771f), scale(63.595f))
            cubicTo(scale(145.691f), scale(63.717f), scale(144.525f), scale(63.259f), scale(143.96f), scale(62.3f))
            cubicTo(scale(143.727f), scale(61.922f), scale(143.599f), scale(61.487f), scale(143.557f), scale(61.046f))
            close()
        }
        pathNumber6.apply {
            reset()
            moveTo(scale(152.192f), scale(76.42f))
            lineTo(scale(151.082f), scale(76.507f))
            cubicTo(scale(150.98f), scale(75.965f), scale(150.701f), scale(75.403f), scale(150.155f), scale(75.194f))
            cubicTo(scale(149.492f), scale(74.908f), scale(148.681f), scale(75.144f), scale(148.244f), scale(75.71f))
            cubicTo(scale(147.566f), scale(76.503f), scale(147.473f), scale(77.598f), scale(147.46f), scale(78.597f))
            cubicTo(scale(148.083f), scale(77.565f), scale(149.462f), scale(77.121f), scale(150.581f), scale(77.546f))
            cubicTo(scale(151.617f), scale(77.934f), scale(152.318f), scale(78.997f), scale(152.346f), scale(80.093f))
            cubicTo(scale(152.429f), scale(81.277f), scale(151.904f), scale(82.585f), scale(150.806f), scale(83.141f))
            cubicTo(scale(149.635f), scale(83.728f), scale(148.067f), scale(83.447f), scale(147.231f), scale(82.417f))
            cubicTo(scale(146.525f), scale(81.557f), scale(146.392f), scale(80.393f), scale(146.354f), scale(79.32f))
            cubicTo(scale(146.342f), scale(78.03f), scale(146.421f), scale(76.658f), scale(147.096f), scale(75.52f))
            cubicTo(scale(147.592f), scale(74.676f), scale(148.546f), scale(74.149f), scale(149.523f), scale(74.158f))
            cubicTo(scale(150.407f), scale(74.109f), scale(151.34f), scale(74.495f), scale(151.806f), scale(75.274f))
            cubicTo(scale(152.022f), scale(75.62f), scale(152.141f), scale(76.018f), scale(152.192f), scale(76.42f))
            close()
            moveTo(scale(147.634f), scale(80.34f))
            cubicTo(scale(147.625f), scale(81.207f), scale(148.084f), scale(82.162f), scale(148.958f), scale(82.436f))
            cubicTo(scale(149.635f), scale(82.684f), scale(150.426f), scale(82.394f), scale(150.823f), scale(81.803f))
            cubicTo(scale(151.265f), scale(81.156f), scale(151.304f), scale(80.294f), scale(151.087f), scale(79.56f))
            cubicTo(scale(150.857f), scale(78.815f), scale(150.083f), scale(78.293f), scale(149.305f), scale(78.379f))
            cubicTo(scale(148.404f), scale(78.42f), scale(147.673f), scale(79.241f), scale(147.642f), scale(80.125f))
            cubicTo(scale(147.636f), scale(80.196f), scale(147.634f), scale(80.268f), scale(147.634f), scale(80.34f))
            close()
        }
        pathNumber10.apply {
            reset()
            moveTo(scale(141.074f), scale(104.485f))
            lineTo(scale(139.958f), scale(104.485f))
            lineTo(scale(139.958f), scale(97.373f))
            cubicTo(scale(139.311f), scale(97.959f), scale(138.533f), scale(98.394f), scale(137.725f), scale(98.718f))
            lineTo(scale(137.725f), scale(97.639f))
            cubicTo(scale(138.779f), scale(97.134f), scale(139.785f), scale(96.399f), scale(140.355f), scale(95.357f))
            lineTo(scale(141.074f), scale(95.357f))
            lineTo(scale(141.074f), scale(104.485f))
            close()
            moveTo(scale(143.939f), scale(100.002f))
            cubicTo(scale(143.952f), scale(98.652f), scale(144.056f), scale(97.15f), scale(145.021f), scale(96.106f))
            cubicTo(scale(145.902f), scale(95.177f), scale(147.5f), scale(95.113f), scale(148.513f), scale(95.863f))
            cubicTo(scale(149.477f), scale(96.644f), scale(149.772f), scale(97.955f), scale(149.843f), scale(99.138f))
            cubicTo(scale(149.893f), scale(100.524f), scale(149.911f), scale(102.002f), scale(149.246f), scale(103.261f))
            cubicTo(scale(148.81f), scale(104.125f), scale(147.874f), scale(104.687f), scale(146.903f), scale(104.64f))
            cubicTo(scale(145.92f), scale(104.681f), scale(144.954f), scale(104.116f), scale(144.533f), scale(103.222f))
            cubicTo(scale(144.035f), scale(102.23f), scale(143.948f), scale(101.094f), scale(143.939f), scale(100.002f))
            close()
            moveTo(scale(145.086f), scale(100.002f))
            cubicTo(scale(145.108f), scale(100.972f), scale(145.096f), scale(102f), scale(145.544f), scale(102.886f))
            cubicTo(scale(145.924f), scale(103.602f), scale(146.89f), scale(103.974f), scale(147.61f), scale(103.536f))
            cubicTo(scale(148.446f), scale(103.052f), scale(148.608f), scale(102.016f), scale(148.678f), scale(101.14f))
            cubicTo(scale(148.743f), scale(99.921f), scale(148.783f), scale(98.662f), scale(148.424f), scale(97.482f))
            cubicTo(scale(148.177f), scale(96.672f), scale(147.284f), scale(96.06f), scale(146.44f), scale(96.345f))
            cubicTo(scale(145.603f), scale(96.6f), scale(145.306f), scale(97.535f), scale(145.197f), scale(98.312f))
            cubicTo(scale(145.111f), scale(98.871f), scale(145.087f), scale(99.437f), scale(145.086f), scale(100.002f))
            close()
        }
        pathNumber15.apply {
            reset()
            moveTo(scale(131.155f), scale(123.74f))
            lineTo(scale(130.038f), scale(123.74f))
            lineTo(scale(130.038f), scale(116.628f))
            cubicTo(scale(129.391f), scale(117.214f), scale(128.614f), scale(117.649f), scale(127.806f), scale(117.973f))
            lineTo(scale(127.806f), scale(116.894f))
            cubicTo(scale(128.86f), scale(116.389f), scale(129.866f), scale(115.654f), scale(130.435f), scale(114.612f))
            lineTo(scale(131.155f), scale(114.612f))
            lineTo(scale(131.155f), scale(123.74f))
            close()
            moveTo(scale(134.02f), scale(121.359f))
            cubicTo(scale(134.41f), scale(121.326f), scale(134.801f), scale(121.293f), scale(135.192f), scale(121.26f))
            cubicTo(scale(135.266f), scale(122.042f), scale(135.794f), scale(122.837f), scale(136.618f), scale(122.951f))
            cubicTo(scale(137.641f), scale(123.165f), scale(138.657f), scale(122.354f), scale(138.808f), scale(121.347f))
            cubicTo(scale(138.997f), scale(120.454f), scale(138.753f), scale(119.354f), scale(137.876f), scale(118.915f))
            cubicTo(scale(137.001f), scale(118.463f), scale(135.796f), scale(118.723f), scale(135.266f), scale(119.58f))
            cubicTo(scale(134.917f), scale(119.534f), scale(134.567f), scale(119.489f), scale(134.218f), scale(119.443f))
            lineTo(scale(135.099f), scale(114.774f))
            lineTo(scale(139.619f), scale(114.774f))
            lineTo(scale(139.619f), scale(115.84f))
            lineTo(scale(135.992f), scale(115.84f))
            cubicTo(scale(135.828f), scale(116.655f), scale(135.665f), scale(117.469f), scale(135.502f), scale(118.283f))
            cubicTo(scale(136.59f), scale(117.455f), scale(138.281f), scale(117.526f), scale(139.229f), scale(118.538f))
            cubicTo(scale(140.24f), scale(119.547f), scale(140.279f), scale(121.254f), scale(139.572f), scale(122.443f))
            cubicTo(scale(139.046f), scale(123.368f), scale(138.001f), scale(123.935f), scale(136.94f), scale(123.896f))
            cubicTo(scale(135.851f), scale(123.937f), scale(134.715f), scale(123.362f), scale(134.271f), scale(122.335f))
            cubicTo(scale(134.131f), scale(122.028f), scale(134.052f), scale(121.695f), scale(134.02f), scale(121.359f))
            close()
        }
        pathNumber2.apply {
            reset()
            moveTo(scale(122.897f), scale(138.227f))
            lineTo(scale(122.897f), scale(139.3f))
            lineTo(scale(116.888f), scale(139.3f))
            cubicTo(scale(116.871f), scale(138.444f), scale(117.401f), scale(137.692f), scale(117.957f), scale(137.086f))
            cubicTo(scale(119.017f), scale(135.959f), scale(120.399f), scale(135.154f), scale(121.31f), scale(133.884f))
            cubicTo(scale(121.721f), scale(133.308f), scale(121.92f), scale(132.489f), scale(121.512f), scale(131.859f))
            cubicTo(scale(121.048f), scale(131.097f), scale(120.002f), scale(130.924f), scale(119.221f), scale(131.249f))
            cubicTo(scale(118.564f), scale(131.515f), scale(118.226f), scale(132.239f), scale(118.246f), scale(132.919f))
            cubicTo(scale(117.864f), scale(132.88f), scale(117.482f), scale(132.841f), scale(117.099f), scale(132.801f))
            cubicTo(scale(117.157f), scale(131.907f), scale(117.586f), scale(130.987f), scale(118.403f), scale(130.552f))
            cubicTo(scale(119.238f), scale(130.105f), scale(120.26f), scale(130.072f), scale(121.158f), scale(130.332f))
            cubicTo(scale(122.168f), scale(130.633f), scale(122.922f), scale(131.635f), scale(122.885f), scale(132.696f))
            cubicTo(scale(122.901f), scale(133.706f), scale(122.266f), scale(134.567f), scale(121.574f), scale(135.241f))
            cubicTo(scale(120.702f), scale(136.129f), scale(119.669f), scale(136.842f), scale(118.806f), scale(137.738f))
            cubicTo(scale(118.741f), scale(137.865f), scale(118.275f), scale(138.249f), scale(118.57f), scale(138.227f))
            lineTo(scale(122.897f), scale(138.227f))
            close()
        }
        pathNumber17.apply {
            reset()
            moveTo(scale(97.701f), scale(149.025f))
            lineTo(scale(96.585f), scale(149.025f))
            lineTo(scale(96.585f), scale(141.912f))
            cubicTo(scale(95.938f), scale(142.499f), scale(95.16f), scale(142.934f), scale(94.353f), scale(143.258f))
            lineTo(scale(94.353f), scale(142.179f))
            cubicTo(scale(95.406f), scale(141.674f), scale(96.412f), scale(140.939f), scale(96.982f), scale(139.897f))
            lineTo(scale(97.701f), scale(139.897f))
            lineTo(scale(97.701f), scale(149.025f))
            close()
            moveTo(scale(100.641f), scale(141.125f))
            lineTo(scale(100.641f), scale(140.052f))
            lineTo(scale(106.525f), scale(140.052f))
            lineTo(scale(106.525f), scale(140.92f))
            cubicTo(scale(105.187f), scale(142.411f), scale(104.235f), scale(144.227f), scale(103.609f), scale(146.123f))
            cubicTo(scale(103.3f), scale(147.061f), scale(103.124f), scale(148.04f), scale(103.059f), scale(149.025f))
            lineTo(scale(101.912f), scale(149.025f))
            cubicTo(scale(101.991f), scale(147.264f), scale(102.503f), scale(145.534f), scale(103.261f), scale(143.949f))
            cubicTo(scale(103.76f), scale(142.942f), scale(104.352f), scale(141.973f), scale(105.093f), scale(141.125f))
            lineTo(scale(100.641f), scale(141.125f))
            close()
        }
        pathNumber3.apply {
            reset()
            moveTo(scale(76.387f), scale(151.099f))
            cubicTo(scale(76.759f), scale(151.049f), scale(77.131f), scale(151f), scale(77.503f), scale(150.95f))
            cubicTo(scale(77.631f), scale(151.635f), scale(77.957f), scale(152.39f), scale(78.669f), scale(152.632f))
            cubicTo(scale(79.438f), scale(152.915f), scale(80.366f), scale(152.623f), scale(80.823f), scale(151.939f))
            cubicTo(scale(81.386f), scale(151.148f), scale(81.239f), scale(149.886f), scale(80.385f), scale(149.353f))
            cubicTo(scale(79.857f), scale(148.996f), scale(79.177f), scale(149.024f), scale(78.589f), scale(149.195f))
            cubicTo(scale(78.63f), scale(148.868f), scale(78.671f), scale(148.542f), scale(78.713f), scale(148.215f))
            cubicTo(scale(79.393f), scale(148.273f), scale(80.177f), scale(148.048f), scale(80.566f), scale(147.453f))
            cubicTo(scale(80.935f), scale(146.795f), scale(80.739f), scale(145.842f), scale(80.048f), scale(145.479f))
            cubicTo(scale(79.301f), scale(145.064f), scale(78.217f), scale(145.327f), scale(77.853f), scale(146.134f))
            cubicTo(scale(77.657f), scale(146.329f), scale(77.746f), scale(146.853f), scale(77.528f), scale(146.91f))
            cubicTo(scale(77.185f), scale(146.849f), scale(76.842f), scale(146.788f), scale(76.499f), scale(146.727f))
            cubicTo(scale(76.64f), scale(145.796f), scale(77.231f), scale(144.907f), scale(78.132f), scale(144.567f))
            cubicTo(scale(79.292f), scale(144.123f), scale(80.802f), scale(144.423f), scale(81.517f), scale(145.493f))
            cubicTo(scale(82.065f), scale(146.284f), scale(82.029f), scale(147.473f), scale(81.298f), scale(148.141f))
            cubicTo(scale(81.158f), scale(148.329f), scale(80.722f), scale(148.495f), scale(80.691f), scale(148.587f))
            cubicTo(scale(81.494f), scale(148.756f), scale(82.152f), scale(149.432f), scale(82.288f), scale(150.243f))
            cubicTo(scale(82.483f), scale(151.198f), scale(82.164f), scale(152.246f), scale(81.417f), scale(152.886f))
            cubicTo(scale(80.513f), scale(153.713f), scale(79.111f), scale(153.87f), scale(77.998f), scale(153.408f))
            cubicTo(scale(77.08f), scale(153.014f), scale(76.459f), scale(152.089f), scale(76.387f), scale(151.099f))
            close()
        }
        pathNumber19.apply {
            reset()
            moveTo(scale(55.69f), scale(149.803f))
            lineTo(scale(54.574f), scale(149.803f))
            lineTo(scale(54.574f), scale(142.69f))
            cubicTo(scale(53.926f), scale(143.277f), scale(53.149f), scale(143.712f), scale(52.341f), scale(144.036f))
            lineTo(scale(52.341f), scale(142.957f))
            cubicTo(scale(53.395f), scale(142.452f), scale(54.401f), scale(141.717f), scale(54.97f), scale(140.675f))
            lineTo(scale(55.69f), scale(140.675f))
            lineTo(scale(55.69f), scale(149.803f))
            close()
            moveTo(scale(58.722f), scale(147.701f))
            cubicTo(scale(59.08f), scale(147.668f), scale(59.437f), scale(147.635f), scale(59.795f), scale(147.602f))
            cubicTo(scale(59.875f), scale(148.234f), scale(60.268f), scale(148.904f), scale(60.947f), scale(149.007f))
            cubicTo(scale(61.808f), scale(149.202f), scale(62.711f), scale(148.659f), scale(63.014f), scale(147.842f))
            cubicTo(scale(63.318f), scale(147.113f), scale(63.45f), scale(146.313f), scale(63.416f), scale(145.524f))
            cubicTo(scale(62.752f), scale(146.625f), scale(61.226f), scale(147.041f), scale(60.086f), scale(146.462f))
            cubicTo(scale(59.076f), scale(145.976f), scale(58.513f), scale(144.841f), scale(58.555f), scale(143.745f))
            cubicTo(scale(58.514f), scale(142.631f), scale(59.075f), scale(141.474f), scale(60.1f), scale(140.972f))
            cubicTo(scale(61.417f), scale(140.3f), scale(63.207f), scale(140.794f), scale(63.937f), scale(142.099f))
            cubicTo(scale(64.596f), scale(143.243f), scale(64.549f), scale(144.623f), scale(64.509f), scale(145.899f))
            cubicTo(scale(64.439f), scale(147.183f), scale(64.132f), scale(148.619f), scale(63.026f), scale(149.419f))
            cubicTo(scale(62.05f), scale(150.114f), scale(60.594f), scale(150.168f), scale(59.621f), scale(149.44f))
            cubicTo(scale(59.082f), scale(149.024f), scale(58.8f), scale(148.363f), scale(58.722f), scale(147.701f))
            close()
            moveTo(scale(63.292f), scale(143.689f))
            cubicTo(scale(63.346f), scale(142.775f), scale(62.769f), scale(141.764f), scale(61.812f), scale(141.619f))
            cubicTo(scale(60.744f), scale(141.427f), scale(59.789f), scale(142.407f), scale(59.721f), scale(143.432f))
            cubicTo(scale(59.574f), scale(144.316f), scale(60.002f), scale(145.333f), scale(60.892f), scale(145.632f))
            cubicTo(scale(61.719f), scale(145.943f), scale(62.758f), scale(145.569f), scale(63.096f), scale(144.728f))
            cubicTo(scale(63.242f), scale(144.403f), scale(63.292f), scale(144.043f), scale(63.292f), scale(143.689f))
            close()
        }
        pathNumber7.apply {
            reset()
            moveTo(scale(36.778f), scale(131.789f))
            lineTo(scale(36.778f), scale(130.716f))
            lineTo(scale(42.663f), scale(130.716f))
            lineTo(scale(42.663f), scale(131.584f))
            cubicTo(scale(41.622f), scale(132.726f), scale(40.844f), scale(134.085f), scale(40.227f), scale(135.495f))
            cubicTo(scale(39.658f), scale(136.825f), scale(39.279f), scale(138.242f), scale(39.196f), scale(139.689f))
            lineTo(scale(38.049f), scale(139.689f))
            cubicTo(scale(38.1f), scale(138.354f), scale(38.434f), scale(137.041f), scale(38.89f), scale(135.789f))
            cubicTo(scale(39.447f), scale(134.343f), scale(40.218f), scale(132.967f), scale(41.23f), scale(131.789f))
            lineTo(scale(36.778f), scale(131.789f))
            close()
        }
        pathNumber16.apply {
            reset()
            moveTo(scale(22.431f), scale(123.546f))
            lineTo(scale(21.315f), scale(123.546f))
            lineTo(scale(21.315f), scale(116.433f))
            cubicTo(scale(20.667f), scale(117.02f), scale(19.89f), scale(117.454f), scale(19.082f), scale(117.779f))
            lineTo(scale(19.082f), scale(116.7f))
            cubicTo(scale(20.136f), scale(116.195f), scale(21.142f), scale(115.46f), scale(21.711f), scale(114.418f))
            lineTo(scale(22.431f), scale(114.418f))
            lineTo(scale(22.431f), scale(123.546f))
            close()
            moveTo(scale(31.088f), scale(116.681f))
            cubicTo(scale(30.718f), scale(116.71f), scale(30.348f), scale(116.739f), scale(29.978f), scale(116.768f))
            cubicTo(scale(29.874f), scale(116.12f), scale(29.445f), scale(115.475f), scale(28.758f), scale(115.365f))
            cubicTo(scale(27.961f), scale(115.183f), scale(27.185f), scale(115.712f), scale(26.838f), scale(116.409f))
            cubicTo(scale(26.424f), scale(117.152f), scale(26.372f), scale(118.025f), scale(26.356f), scale(118.858f))
            cubicTo(scale(27.087f), scale(117.625f), scale(28.9f), scale(117.261f), scale(30.05f), scale(118.113f))
            cubicTo(scale(30.976f), scale(118.745f), scale(31.371f), scale(119.931f), scale(31.226f), scale(121.015f))
            cubicTo(scale(31.113f), scale(122.202f), scale(30.3f), scale(123.379f), scale(29.085f), scale(123.626f))
            cubicTo(scale(27.881f), scale(123.917f), scale(26.505f), scale(123.414f), scale(25.873f), scale(122.324f))
            cubicTo(scale(25.192f), scale(121.1f), scale(25.205f), scale(119.64f), scale(25.286f), scale(118.281f))
            cubicTo(scale(25.391f), scale(117.016f), scale(25.746f), scale(115.602f), scale(26.873f), scale(114.867f))
            cubicTo(scale(27.824f), scale(114.273f), scale(29.149f), scale(114.243f), scale(30.086f), scale(114.878f))
            cubicTo(scale(30.677f), scale(115.291f), scale(31.006f), scale(115.979f), scale(31.088f), scale(116.681f))
            close()
            moveTo(scale(26.53f), scale(120.6f))
            cubicTo(scale(26.512f), scale(121.528f), scale(27.059f), scale(122.551f), scale(28.027f), scale(122.745f))
            cubicTo(scale(28.994f), scale(122.995f), scale(29.935f), scale(122.175f), scale(30.058f), scale(121.235f))
            cubicTo(scale(30.237f), scale(120.344f), scale(30.001f), scale(119.236f), scale(29.116f), scale(118.807f))
            cubicTo(scale(28.238f), scale(118.359f), scale(27.061f), scale(118.8f), scale(26.696f), scale(119.718f))
            cubicTo(scale(26.577f), scale(119.995f), scale(26.529f), scale(120.299f), scale(26.53f), scale(120.6f))
            close()
        }
        pathNumber8.apply {
            reset()
            moveTo(scale(12.278f), scale(100.333f))
            cubicTo(scale(11.594f), scale(100.116f), scale(11.008f), scale(99.532f), scale(10.935f), scale(98.796f))
            cubicTo(scale(10.794f), scale(97.906f), scale(11.236f), scale(96.971f), scale(12.013f), scale(96.515f))
            cubicTo(scale(12.803f), scale(96.054f), scale(13.808f), scale(96.028f), scale(14.658f), scale(96.331f))
            cubicTo(scale(15.591f), scale(96.682f), scale(16.258f), scale(97.661f), scale(16.161f), scale(98.666f))
            cubicTo(scale(16.137f), scale(99.425f), scale(15.574f), scale(100.079f), scale(14.869f), scale(100.317f))
            cubicTo(scale(14.979f), scale(100.418f), scale(15.439f), scale(100.551f), scale(15.641f), scale(100.746f))
            cubicTo(scale(16.441f), scale(101.33f), scale(16.693f), scale(102.43f), scale(16.458f), scale(103.357f))
            cubicTo(scale(16.19f), scale(104.496f), scale(15.102f), scale(105.327f), scale(13.951f), scale(105.399f))
            cubicTo(scale(12.924f), scale(105.514f), scale(11.788f), scale(105.211f), scale(11.135f), scale(104.365f))
            cubicTo(scale(10.47f), scale(103.547f), scale(10.355f), scale(102.318f), scale(10.843f), scale(101.385f))
            cubicTo(scale(11.138f), scale(100.842f), scale(11.686f), scale(100.479f), scale(12.278f), scale(100.333f))
            close()
            moveTo(scale(12.055f), scale(98.442f))
            cubicTo(scale(12.015f), scale(99.159f), scale(12.591f), scale(99.834f), scale(13.309f), scale(99.891f))
            cubicTo(scale(13.977f), scale(100.005f), scale(14.716f), scale(99.644f), scale(14.94f), scale(98.985f))
            cubicTo(scale(15.204f), scale(98.28f), scale(14.822f), scale(97.426f), scale(14.117f), scale(97.158f))
            cubicTo(scale(13.399f), scale(96.865f), scale(12.466f), scale(97.175f), scale(12.16f), scale(97.912f))
            cubicTo(scale(12.088f), scale(98.079f), scale(12.055f), scale(98.261f), scale(12.055f), scale(98.442f))
            close()
            moveTo(scale(11.695f), scale(102.64f))
            cubicTo(scale(11.683f), scale(103.392f), scale(12.143f), scale(104.157f), scale(12.88f), scale(104.386f))
            cubicTo(scale(13.662f), scale(104.672f), scale(14.643f), scale(104.431f), scale(15.104f), scale(103.711f))
            cubicTo(scale(15.626f), scale(102.915f), scale(15.448f), scale(101.715f), scale(14.648f), scale(101.165f))
            cubicTo(scale(13.861f), scale(100.59f), scale(12.607f), scale(100.726f), scale(12.035f), scale(101.542f))
            cubicTo(scale(11.8f), scale(101.857f), scale(11.693f), scale(102.251f), scale(11.695f), scale(102.64f))
            close()
        }
        pathNumber11.apply {
            reset()
            moveTo(scale(9.399f), scale(84.452f))
            lineTo(scale(8.283f), scale(84.452f))
            lineTo(scale(8.283f), scale(77.339f))
            cubicTo(scale(7.636f), scale(77.926f), scale(6.858f), scale(78.36f), scale(6.051f), scale(78.685f))
            lineTo(scale(6.051f), scale(77.606f))
            cubicTo(scale(7.104f), scale(77.101f), scale(8.108f), scale(76.364f), scale(8.68f), scale(75.324f))
            lineTo(scale(9.399f), scale(75.324f))
            lineTo(scale(9.399f), scale(84.452f))
            close()
            moveTo(scale(15.526f), scale(84.452f))
            lineTo(scale(14.41f), scale(84.452f))
            lineTo(scale(14.41f), scale(77.339f))
            cubicTo(scale(13.763f), scale(77.926f), scale(12.985f), scale(78.36f), scale(12.178f), scale(78.685f))
            lineTo(scale(12.178f), scale(77.606f))
            cubicTo(scale(13.231f), scale(77.101f), scale(14.235f), scale(76.364f), scale(14.807f), scale(75.324f))
            lineTo(scale(15.526f), scale(75.324f))
            lineTo(scale(15.526f), scale(84.452f))
            close()
        }
        pathNumber14.apply {
            reset()
            moveTo(scale(12.9f), scale(64.224f))
            lineTo(scale(11.784f), scale(64.224f))
            lineTo(scale(11.784f), scale(57.112f))
            cubicTo(scale(11.137f), scale(57.698f), scale(10.359f), scale(58.133f), scale(9.552f), scale(58.457f))
            lineTo(scale(9.552f), scale(57.378f))
            cubicTo(scale(10.606f), scale(56.873f), scale(11.611f), scale(56.138f), scale(12.181f), scale(55.096f))
            lineTo(scale(12.9f), scale(55.096f))
            lineTo(scale(12.9f), scale(64.224f))
            close()
            moveTo(scale(19.343f), scale(64.224f))
            lineTo(scale(19.343f), scale(62.048f))
            lineTo(scale(15.399f), scale(62.048f))
            cubicTo(scale(15.411f), scale(61.7f), scale(15.376f), scale(61.338f), scale(15.417f), scale(61f))
            lineTo(scale(19.548f), scale(55.133f))
            lineTo(scale(20.46f), scale(55.133f))
            lineTo(scale(20.46f), scale(61.025f))
            lineTo(scale(21.687f), scale(61.025f))
            lineTo(scale(21.687f), scale(62.048f))
            lineTo(scale(20.46f), scale(62.048f))
            lineTo(scale(20.46f), scale(64.224f))
            lineTo(scale(19.343f), scale(64.224f))
            close()
            moveTo(scale(19.343f), scale(61.025f))
            lineTo(scale(19.343f), scale(56.926f))
            lineTo(scale(16.497f), scale(61.025f))
            lineTo(scale(19.343f), scale(61.025f))
            close()
        }
        pathNumber9.apply {
            reset()
            moveTo(scale(20.533f), scale(40.727f))
            cubicTo(scale(20.891f), scale(40.694f), scale(21.248f), scale(40.661f), scale(21.606f), scale(40.628f))
            cubicTo(scale(21.691f), scale(41.214f), scale(22.011f), scale(41.844f), scale(22.628f), scale(42.002f))
            cubicTo(scale(23.366f), scale(42.223f), scale(24.208f), scale(41.903f), scale(24.619f), scale(41.253f))
            cubicTo(scale(25.101f), scale(40.448f), scale(25.254f), scale(39.478f), scale(25.228f), scale(38.551f))
            cubicTo(scale(24.652f), scale(39.49f), scale(23.445f), scale(39.955f), scale(22.386f), scale(39.668f))
            cubicTo(scale(21.263f), scale(39.394f), scale(20.455f), scale(38.312f), scale(20.385f), scale(37.177f))
            cubicTo(scale(20.27f), scale(36.128f), scale(20.58f), scale(34.968f), scale(21.444f), scale(34.302f))
            cubicTo(scale(22.595f), scale(33.36f), scale(24.456f), scale(33.553f), scale(25.42f), scale(34.675f))
            cubicTo(scale(26.197f), scale(35.549f), scale(26.323f), scale(36.771f), scale(26.342f), scale(37.892f))
            cubicTo(scale(26.343f), scale(39.164f), scale(26.287f), scale(40.521f), scale(25.597f), scale(41.632f))
            cubicTo(scale(25.065f), scale(42.509f), scale(24.042f), scale(43.032f), scale(23.022f), scale(42.984f))
            cubicTo(scale(22.138f), scale(42.998f), scale(21.226f), scale(42.547f), scale(20.83f), scale(41.733f))
            cubicTo(scale(20.669f), scale(41.42f), scale(20.577f), scale(41.075f), scale(20.533f), scale(40.727f))
            close()
            moveTo(scale(25.104f), scale(36.715f))
            cubicTo(scale(25.13f), scale(35.964f), scale(24.793f), scale(35.164f), scale(24.104f), scale(34.804f))
            cubicTo(scale(23.412f), scale(34.428f), scale(22.509f), scale(34.666f), scale(22.031f), scale(35.272f))
            cubicTo(scale(21.477f), scale(35.923f), scale(21.374f), scale(36.898f), scale(21.678f), scale(37.683f))
            cubicTo(scale(21.978f), scale(38.431f), scale(22.822f), scale(38.878f), scale(23.611f), scale(38.743f))
            cubicTo(scale(24.402f), scale(38.656f), scale(24.992f), scale(37.936f), scale(25.072f), scale(37.17f))
            cubicTo(scale(25.094f), scale(37.019f), scale(25.104f), scale(36.867f), scale(25.104f), scale(36.715f))
            close()
        }
        pathNumber12.apply {
            reset()
            moveTo(scale(36.582f), scale(28.574f))
            lineTo(scale(35.466f), scale(28.574f))
            lineTo(scale(35.466f), scale(21.462f))
            cubicTo(scale(34.818f), scale(22.048f), scale(34.041f), scale(22.483f), scale(33.233f), scale(22.807f))
            lineTo(scale(33.233f), scale(21.728f))
            cubicTo(scale(34.287f), scale(21.223f), scale(35.293f), scale(20.488f), scale(35.863f), scale(19.446f))
            lineTo(scale(36.582f), scale(19.446f))
            lineTo(scale(36.582f), scale(28.574f))
            close()
            moveTo(scale(45.313f), scale(27.502f))
            lineTo(scale(45.313f), scale(28.574f))
            lineTo(scale(39.304f), scale(28.574f))
            cubicTo(scale(39.305f), scale(27.465f), scale(40.114f), scale(26.573f), scale(40.879f), scale(25.861f))
            cubicTo(scale(41.898f), scale(24.918f), scale(43.119f), scale(24.141f), scale(43.86f), scale(22.939f))
            cubicTo(scale(44.29f), scale(22.275f), scale(44.267f), scale(21.29f), scale(43.616f), scale(20.774f))
            cubicTo(scale(42.788f), scale(20.065f), scale(41.248f), scale(20.286f), scale(40.82f), scale(21.348f))
            cubicTo(scale(40.586f), scale(21.627f), scale(40.879f), scale(22.355f), scale(40.361f), scale(22.162f))
            lineTo(scale(39.515f), scale(22.076f))
            cubicTo(scale(39.57f), scale(21.114f), scale(40.079f), scale(20.133f), scale(40.993f), scale(19.737f))
            cubicTo(scale(41.919f), scale(19.342f), scale(43.032f), scale(19.333f), scale(43.949f), scale(19.759f))
            cubicTo(scale(44.909f), scale(20.217f), scale(45.462f), scale(21.335f), scale(45.269f), scale(22.377f))
            cubicTo(scale(45.064f), scale(23.646f), scale(44.01f), scale(24.513f), scale(43.109f), scale(25.329f))
            cubicTo(scale(42.344f), scale(26.035f), scale(41.432f), scale(26.616f), scale(40.854f), scale(27.502f))
            lineTo(scale(45.313f), scale(27.502f))
            close()
        }
        pathNumber5.apply {
            reset()
            moveTo(scale(54.986f), scale(16.136f))
            cubicTo(scale(55.377f), scale(16.103f), scale(55.768f), scale(16.07f), scale(56.158f), scale(16.037f))
            cubicTo(scale(56.242f), scale(16.738f), scale(56.641f), scale(17.465f), scale(57.358f), scale(17.673f))
            cubicTo(scale(58.114f), scale(17.923f), scale(58.994f), scale(17.61f), scale(59.432f), scale(16.947f))
            cubicTo(scale(59.884f), scale(16.28f), scale(59.936f), scale(15.384f), scale(59.677f), scale(14.634f))
            cubicTo(scale(59.415f), scale(13.907f), scale(58.65f), scale(13.443f), scale(57.888f), scale(13.476f))
            cubicTo(scale(57.236f), scale(13.465f), scale(56.584f), scale(13.804f), scale(56.233f), scale(14.356f))
            cubicTo(scale(55.883f), scale(14.311f), scale(55.534f), scale(14.266f), scale(55.185f), scale(14.22f))
            lineTo(scale(56.065f), scale(9.551f))
            lineTo(scale(60.586f), scale(9.551f))
            lineTo(scale(60.586f), scale(10.617f))
            lineTo(scale(56.958f), scale(10.617f))
            lineTo(scale(56.468f), scale(13.06f))
            cubicTo(scale(57.445f), scale(12.331f), scale(58.898f), scale(12.281f), scale(59.875f), scale(13.034f))
            cubicTo(scale(60.676f), scale(13.611f), scale(61.074f), scale(14.619f), scale(61.011f), scale(15.588f))
            cubicTo(scale(60.995f), scale(16.938f), scale(60.062f), scale(18.293f), scale(58.708f), scale(18.585f))
            cubicTo(scale(57.621f), scale(18.827f), scale(56.344f), scale(18.597f), scale(55.61f), scale(17.702f))
            cubicTo(scale(55.238f), scale(17.265f), scale(55.038f), scale(16.703f), scale(54.986f), scale(16.136f))
            close()
        }*/
    }
    
    override fun draw(canvas: Canvas) {
        canvas.apply {
            /*drawPath(path20, paint1_0)
            drawPath(path1, paint2_0)
            drawPath(path18, paint1_0)
            drawPath(path4, paint2_0)
            drawPath(path13, paint1_0)
            drawPath(path6, paint2_0)
            drawPath(path10, paint1_0)
            drawPath(path15, paint2_0)
            drawPath(path2, paint1_0)
            drawPath(path17, paint2_0)
            drawPath(path3, paint1_0)
            drawPath(path19, paint2_0)
            drawPath(path7, paint1_0)
            drawPath(path16, paint2_0)
            drawPath(path8, paint1_0)
            drawPath(path11, paint2_0)
            drawPath(path14, paint1_0)
            drawPath(path9, paint2_0)
            drawPath(path12, paint1_0)
            drawPath(path5, paint2_0)*/
            
            paths.forEach {
                drawPath(it.path, it.paint)
            }
            
            drawPath(path25, boarderPaint)
            drawPath(outerPath, boarderPaint)
            
            //drawPath(pathNumber20, numberPaint)
            drawText("20", scale(74.5f), scale(12.95f), numberPaint)
            drawText("1", scale(98f), scale(16f), numberPaint)
            drawText("18", scale(115f), scale(26f), numberPaint)
            drawText("4", scale(133.5f), scale(41f), numberPaint)
            drawText("13", scale(140f), scale(62f), numberPaint)
            drawText("6", scale(147f), scale(83f), numberPaint)
            drawText("10", scale(139.5f), scale(103.5f), numberPaint)
            drawText("15", scale(129f), scale(122.5f), numberPaint)
            drawText("2", scale(118f), scale(138.227f), numberPaint)
            drawText("17", scale(95.5f), scale(149.025f), numberPaint)
            drawText("3", scale(76.387f), scale(151.099f), numberPaint)
            drawText("19", scale(53f), scale(147.5f), numberPaint)
            drawText("7", scale(36.778f), scale(137f), numberPaint)
            drawText("16", scale(19f), scale(122f), numberPaint)
            drawText("8", scale(11f), scale(104f), numberPaint)
            drawText("11", scale(6f), scale(83f), numberPaint)
            drawText("14", scale(10f), scale(62f), numberPaint)
            drawText("9", scale(20.533f), scale(41f), numberPaint)
            drawText("12", scale(34f), scale(27f), numberPaint)
            drawText("5", scale(54.986f), scale(16.136f), numberPaint)
            //drawPath(pathNumber1, numberPaint)
            //drawPath(pathNumber18, numberPaint)
            //drawPath(pathNumber4, numberPaint)
            //drawPath(pathNumber13, numberPaint)
            //drawPath(pathNumber6, numberPaint)
            //drawPath(pathNumber10, numberPaint)
            //drawPath(pathNumber15, numberPaint)
            //drawPath(pathNumber2, numberPaint)
            //drawPath(pathNumber17, numberPaint)
            //drawPath(pathNumber3, numberPaint)
            //drawPath(pathNumber19, numberPaint)
            //drawPath(pathNumber7, numberPaint)
            //drawPath(pathNumber8, numberPaint)
            //drawPath(pathNumber16, numberPaint)
            //drawPath(pathNumber11, numberPaint)
            //drawPath(pathNumber14, numberPaint)
            //drawPath(pathNumber9, numberPaint)
            //drawPath(pathNumber12, numberPaint)
            //drawPath(pathNumber5, numberPaint)
        }
    }
    
    override fun setAlpha(alpha: Int) {
    }
    
    override fun getOpacity(): Int = PixelFormat.UNKNOWN
    
    override fun setColorFilter(colorFilter: ColorFilter?) {
    }
    
    companion object {
        private const val TAG = "DartBoardDrawable"
        
        const val STATE_NOT_PRESSED = 0
        const val STATE_ONCE_PRESSED = 1
        const val STATE_TWICE_PRESSED = 2
        const val STATE_THRICE_PRESSED = 3
    }
    
}
