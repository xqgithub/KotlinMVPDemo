package com.example.baselibrary.utils

import android.content.Context
import android.content.Intent
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

