package com.example.baselibrary.utils

import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.View
import io.reactivex.Observable
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




