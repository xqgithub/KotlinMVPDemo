package com.example.testlibrary.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.blankj.utilcode.util.LogUtils
import com.example.baselibrary.constants.ConfigConstants

/**
 * Date:2023/4/19
 * Time:15:29
 * author:joker
 * 测试 Messenger通信
 */
class MessengerService : Service() {

    companion object {
        const val MSG_SAY_HELLO = 0
    }

    private lateinit var messenger: Messenger

    override fun onBind(intent: Intent?): IBinder {
        LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onBind")
        messenger = Messenger(IncomingHander)
        return messenger.binder
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onDestroy")
    }

    //实现一个handler
    object IncomingHander : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when (msg.what) {
                MSG_SAY_HELLO -> {
                    val bundle = msg.data
                    val name = bundle.getString("name")
                    LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "来自Activity的消息 = $name")
                }
            }
        }
    }
}