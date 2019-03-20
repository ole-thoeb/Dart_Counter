package com.example.eloem.dartCounter.helperClasses

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class LastLineBaselineTextView @JvmOverloads constructor(context: Context,
                                                         attrs: AttributeSet? = null,
                                                         defStyleAttr: Int = 0,
                                                         defStyleRes: Int = 0):
        TextView(context, attrs, defStyleAttr, defStyleRes) {
    
    override fun getBaseline(): Int {
        val layout = layout ?: return super.getBaseline()
        val baselineOffset = super.getBaseline() - layout.getLineBaseline(0)
        return baselineOffset + layout.getLineBaseline(layout.lineCount - 1)
    }
}