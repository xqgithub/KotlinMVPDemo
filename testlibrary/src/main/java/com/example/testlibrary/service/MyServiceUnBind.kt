package com.example.testlibrary.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils
import com.example.baselibrary.constants.ConfigConstants.GLOBAL_TAG

/**
 * Date:2023/4/14
 * Time:15:58
 * author:joker
 *  测试startService
 */
class MyServiceUnBind : Service() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.iTag(GLOBAL_TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.iTag(GLOBAL_TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        LogUtils.iTag(GLOBAL_TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}