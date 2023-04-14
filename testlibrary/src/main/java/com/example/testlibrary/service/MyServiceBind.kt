package com.example.testlibrary.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.ConfigConstants.GLOBAL_TAG

/**
 * Date:2023/4/14
 * Time:16:12
 * author:joker
 * 测试绑定Service
 */
class MyServiceBind : Service() {

    private var iBinder = MyBinder(this@MyServiceBind)

    override fun onCreate() {
        super.onCreate()
        LogUtils.iTag(GLOBAL_TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.iTag(GLOBAL_TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder {
        LogUtils.iTag(GLOBAL_TAG, "onBind")
        return iBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogUtils.iTag(GLOBAL_TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        LogUtils.iTag(GLOBAL_TAG, "onDestroy")
        super.onDestroy()
    }


    class MyBinder(private var myServiceBind: MyServiceBind) : Binder() {
        fun getService(): MyServiceBind {
            return myServiceBind
        }
    }

    fun startDownload() {
        LogUtils.iTag(GLOBAL_TAG, "开始下载任务")
    }


}