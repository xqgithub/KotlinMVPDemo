package com.example.baselibrary.utils

import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import com.example.baselibrary.R
import com.example.baselibrary.mvp.view.loadsir.EmptyCallback
import com.example.baselibrary.mvp.view.loadsir.ErrorCallback
import com.example.baselibrary.mvp.view.loadsir.LoadingCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * 顶层函数集合的工具类
 */

/**
 * 00001
 * RXJAVA2网络请求封装
 */
fun <T> runRx(observable: Observable<T>, subscriber: Observer<T>) =
    observable.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)

/**
 * 00002
 * activity页面间的跳转
 */
fun intentToJump(context: Context, cls: Class<*>, flag: Int) {
    val intent = Intent(context, cls)
    intent.flags = flag
    context.startActivity(intent)
}

/**
 * 00003
 * Kotlin 实现 ViewHolder 的扩展函数 实现和使用起来更加方便流畅，甚至都感觉不到 ViewHolder 这种特殊机制的存在
 */
fun <T : View> View.findViewOfItem(viewId: Int): T {
    var viewHolder: SparseArray<View> = tag as? SparseArray<View> ?: SparseArray()
    tag = viewHolder
    var childView: View? = viewHolder.get(viewId)
    if (StringUtils.isBlank(childView)) {
        childView = findViewById(viewId)
        viewHolder.put(viewId, childView)
    }
    return childView as T
}

/**00004 kotlin 防止重复点击 start **/
/***
 * 设置延迟时间的View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @return T
 */
fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}

/***
 * 点击事件的View扩展
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
    block(it as T)
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else -601
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else 600
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

/***
 * 带延迟过滤的点击事件监听，见[View.OnClickListener]
 * 延迟时间根据triggerDelay获取：600毫秒，不能动态设置
 */
interface OnLazyClickListener : View.OnClickListener {

    override fun onClick(v: View?) {
        if (v?.clickEnable() == true) {
            onLazyClick(v)
        }
    }

    fun onLazyClick(v: View)
}
/**00004 kotlin 防止重复点击 end **/


/**
 * 00005
 * Observable 切换到主线程
 */
fun <T> observableToMain(): ObservableTransformer<T, T> {
    return ObservableTransformer { ob ->
        ob.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

/**
 * 00006
 * Flowable 切换到主线程
 */
fun <T> flowableToMain(): FlowableTransformer<T, T> {
    return FlowableTransformer { fb ->
        fb.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

/**
 * 00007
 *
 */
fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadsir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadsir.showSuccess()
    return loadsir
}

fun loadServiceInit(view: Any, callback: () -> Unit): LoadService<Any> {
    val loadsir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadsir.showSuccess()
    return loadsir
}


/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.showCallback(ErrorCallback::class.java)
}


/**
 * 自定义TAG 标签名
 */
inline fun mTAG(tagName: String = ""): String {
    return "=-= $tagName =-="
}













