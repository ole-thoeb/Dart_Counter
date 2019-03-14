package com.example.eloem.dartCounter.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Environment
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


/*
fun writeGameAndSetFlag(context: Context, game: DartGame){
    writeCurrentGame(context, game)
    writeThereIsGame(context, true)
}*/

fun hideSoftKeyboard(context: Context, view: View?){
    val ipm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    ipm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun View.focusAndShowKeyboard() {
    requestFocusFromTouch()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun differentShade (color: Int, difference: Float): Int{
    val factor = 1 + difference
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] = hsv[2] * factor
    return Color.HSVToColor(hsv)
}

fun Context.getAttribute(@AttrRes resourceId: Int, resolveRef: Boolean = true): TypedValue {
    val tv = TypedValue()
    theme.resolveAttribute(resourceId, tv, resolveRef)
    return tv
}

/**
 * converts a pixel int to a dp int
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
/**
 * converts a dp int to a pixel int
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
/*
fun Array<DartGame.Point>.throwsLeft(): Int{
    var throwsLeft = 0
    
    forEachIndexed { index, point ->
        if (point.value == 0){
            if (index + 1 < size){
                if (get(index + 1).value == 0) throwsLeft ++
            }else throwsLeft ++
        }
    }
    return throwsLeft
}

fun Array<DartGame.Point>.alreadyThrown():List<DartGame.Point>{
    val list = mutableListOf<DartGame.Point>()
    
    for ((index, point) in this.withIndex()) {
        if (point.value == 0){
            if (index + 1 < size){
                if (get(index + 1).value != 0) list.add(get(index))
            }
        }else list.add(get(index))
    }
    return list.toList()
}*/

fun writeDatabase(context: Context){
    val sd = Environment.getExternalStorageDirectory()
    
    val DB_PATH =  context.filesDir.absolutePath.replace("files", "databases") + File.separator
    if (sd.canWrite()) {
        val currentDBPath = "MyDatabase"
        val backupDBPath = "backupname.db"
        val currentDB = File(DB_PATH, currentDBPath)
        val backupDB = File(sd, backupDBPath)
        
        if (currentDB.exists()) {
            println("asdafasfasfdasda")
            val src = FileInputStream(currentDB).channel
            val dst = FileOutputStream(backupDB).channel
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
        }
    }
}