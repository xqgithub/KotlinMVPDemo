package com.example.testlibrary.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils
import com.example.baselibrary.constants.ConfigConstants
import com.example.testlibrary.IMyAidlInterface

/**
 * Date:2023/4/15
 * Time:10:17
 * author:joker
 * 测试AIDL功能
 */
class TestAIDLService : Service() {

    var mStub = object : IMyAidlInterface.Stub() {
        override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
        }

        override fun getName(nickName: String?): String {
            var nickname = "aidl =-= $nickName"
            return nickname
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mStub
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onDestroy")
    }

}