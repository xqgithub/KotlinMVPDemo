package com.example.baselibrary.mvp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Date:2021/4/7
 * Time:16:58
 * author:joker
 * 应用移除后做操作处理
 */
class TaskRemovedService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * 当应用被移除的时候，可以做一些操作
     */
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}