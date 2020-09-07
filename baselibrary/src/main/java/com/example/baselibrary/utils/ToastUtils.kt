package example.com.testkotlin.haha.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.baselibrary.application.MyApplication

/**
 * Created by beijixiong on 2018/9/20.
 * 弹出吐司的扩展方法
 */


val sHandler = Handler(Looper.getMainLooper())
var sToast: Toast? = null


/**
 * 显示吐司
 * @param text     文本
 * @param duration 显示时长
 */
fun showToast(context: Context, text: String, duration: Int) {
    cancelToast()
    if (sToast == null) {
        sToast = Toast.makeText(context, text, duration)
    } else {
        sToast!!.setText(text)
        sToast!!.setDuration(duration)
    }
    sToast!!.show()
}

/**
 * 显示吐司
 *@param resId  资源文件
 */

fun showToastByResId(resId: Int, duration: Int) {
    showToast(
        MyApplication.myapplication.applicationContext,
        MyApplication.myapplication.applicationContext.resources.getText(resId).toString(),
        duration
    )
}


/**
 * 安全地显示短时吐司
 * @param text 文本
 */
fun Context.showShortToastSafe(text: String) {
    sHandler.post { showToast(this, text, Toast.LENGTH_SHORT) }
}


/**
 * 安全地显示长时吐司
 * @param text 文本
 */
fun Context.showLongToastSafe(text: String) {
    sHandler.post { showToast(this, text, Toast.LENGTH_LONG) }
}


/**
 * 安全地显示短时吐司
 * @param resId 资源文件
 */
fun Context.showShortToastSafe(resId: Int) {
    sHandler.post { showToastByResId(resId, Toast.LENGTH_SHORT) }
}

/**
 * 安全地显示长时吐司
 * @param resId 资源文件
 */
fun Context.showLongToastSafe(resId: Int) {
    sHandler.post { showToastByResId(resId, Toast.LENGTH_LONG) }
}


/**
 * 取消吐司显示
 */
fun cancelToast() {
    if (sToast != null) {
        sToast!!.cancel()
        sToast = null
    }
}

















