/*
* ECARX Technology Limited is the owner of the copyright and the trade secret of this software. 
* Without permission, no one has the right to obtain, disclose or use this software in any way.
*/

package com.example.baselibrary.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Date:2023/6/14
 * Time:17:38
 * author:qi.xiao
 * PackageName:com.example.baselibrary.utils
 * handler工具类添加
 */
class HandlerPlusUtils<T>(mLooper: Looper = Looper.getMainLooper(), var mContext: T? = null) : Handler(mLooper) {
    private var ref: WeakReference<T>? = null

    private lateinit var onReceiveMessageListener: (msg: Message) -> Unit

    init {
        mContext?.let {
            ref = WeakReference(it)
        }
    }

    /**
     * 获取T的值 一般是 context
     */
    fun getRef(): T? {
        return ref?.get()
    }

    /**
     * 设置消息监听
     */
    fun setOnReceiveMessageListener(onReceiveMessageListener: (msg: Message) -> Unit) {
        this.onReceiveMessageListener = onReceiveMessageListener
    }

    override fun handleMessage(msg: Message) {
//        super.handleMessage(msg)
        if (::onReceiveMessageListener.isInitialized) onReceiveMessageListener.invoke(msg)
    }

    /**
     * 如果有超时任务，退出activity或者fragment 需要销毁handler
     */
    fun removeHandler() {
        this.removeCallbacksAndMessages(null)
        LogUtils.i(mTAG("removeHandler"), "handler is removed")
    }

    /**
     *  handler what 相关的值
     */
    object HandleWhatCode {
//        const val testone = 0x9999
    }

}