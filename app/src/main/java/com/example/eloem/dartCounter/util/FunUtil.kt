package com.example.eloem.dartCounter.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Environment
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.reflect.KProperty


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

fun Fragment.getDimenAttr(@AttrRes resourceId: Int): Int {
    return resources.getDimensionPixelSize(requireContext().getAttribute(resourceId).resourceId)
}

/**
 * converts a pixel int to a dp int
 */
val Int.dp: Int
    get() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                Resources.getSystem().displayMetrics).toInt()
    }
/**
 * converts a dp int to a pixel int
 */
val Int.px: Int
    get() = (this / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))

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

inline fun <reified T: ViewModel>Fragment.fragmentViewModel(): ViewModelDelegateProvider<T> {
    return ViewModelDelegateProvider({ ViewModelProviders.of(this) }, T::class.java)
}

inline fun <reified T: ViewModel>Fragment.activityViewModel(): ViewModelDelegateProvider<T> {
    return ViewModelDelegateProvider({ ViewModelProviders.of(this.requireActivity()) }, T::class.java)
}

class ViewModelDelegateProvider<T: ViewModel>(lazyFactory: () -> ViewModelProvider,
                                              private val viewModelClass: Class<T>): Lazy<T> {
    
    private var cached: T? = null
    
    private val factory by lazy(LazyThreadSafetyMode.NONE, lazyFactory)
    
    override val value: T
        get() {
            return cached ?: factory.get(viewModelClass).also { cached = it }
        }
    
    override fun isInitialized(): Boolean = cached == null
}