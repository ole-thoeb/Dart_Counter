package emil.beothy.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class TwoDivLin: LinearLayout {
    
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)
    
    private var finishedInit = false
    private var mLin1: LinearLayout
    private var mLin2: LinearLayout
    
    private var mLin1Items = 0
    private var mLin2Items = 0
    
    init {
        weightSum = 2f
        mLin1= LinearLayout(context).apply {
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            orientation = LinearLayout.VERTICAL
        }
        mLin2 = LinearLayout(context).apply {
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            orientation = LinearLayout.VERTICAL
        }
    
        addView(mLin1)
        addView(mLin2)
    
        finishedInit = true
    }
    
    override fun addView(child: View?) {
        if (finishedInit){
            if (mLin1Items > mLin2Items){
                mLin2.addView(child)
                mLin2Items ++
            } else{
                mLin1.addView(child)
                mLin1Items ++
            }
        }else super.addView(child)
    }
}