package com.example.eloem.dartCounter.util

import android.content.Context
import android.graphics.Color
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun differentShade (color: Int, difference: Float): Int{
    val factor = 1 + difference
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] = hsv[2] * factor
    return Color.HSVToColor(hsv)
}
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