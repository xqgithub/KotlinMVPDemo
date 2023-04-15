package com.example.testlibrary

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.ConfigConstants.GLOBAL_TAG
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.StringUtils
import com.example.baselibrary.utils.clickWithTrigger
import com.example.testlibrary.service.MyServiceBind
import com.example.testlibrary.service.MyServiceUnBind
import com.example.testlibrary.service.TestAIDLService
import kotlinx.android.synthetic.main.activity_testservice_startupmode.*

/**
 * Date:2023/4/14
 * Time:15:40
 * author:joker
 * 测试 Service 启动模式和生命周期
 * 测试AIDL功能
 */
@Route(path = RouterTag.TestServiceStartupModeActivity)
class TestServiceStartupModeActivity : BaseActivity() {

    private lateinit var serviceIntent: Intent

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            R.color.full_red
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_testservice_startupmode
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvStartService.clickWithTrigger(500) {
            serviceIntent = Intent(this@TestServiceStartupModeActivity, MyServiceUnBind::class.java)
            startService(serviceIntent)
        }
        tvStopService.clickWithTrigger(500) {
            if (!StringUtils.isBlank(serviceIntent)) {
                stopService(serviceIntent)
                serviceIntent == null
            }
        }
        tvBindService.clickWithTrigger(500) {
            Intent(this@TestServiceStartupModeActivity, MyServiceBind::class.java).apply {
                bindService(this, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }
        tvUnBindService.clickWithTrigger(500) {
            if (bingflag) {
                unbindService(serviceConnection)
            }
        }

        tvADIL.clickWithTrigger(500) {
            Intent(this@TestServiceStartupModeActivity, TestAIDLService::class.java).apply {
                bindService(this, aidlServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }

        tvADIL2.clickWithTrigger(500) {
            if (!StringUtils.isBlank(iMyAidlInterface)) {
                try {
                    var nicname = iMyAidlInterface.getName("海贼王路飞")
                    LogUtils.iTag(GLOBAL_TAG, nicname)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**  绑定Service  **/
    private lateinit var myServiceBind: MyServiceBind
    private var bingflag: Boolean = false
    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onServiceConnected")
            bingflag = true
            var myBinder = service as MyServiceBind.MyBinder

            myServiceBind = myBinder.getService()
            myServiceBind.startDownload()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.iTag(ConfigConstants.GLOBAL_TAG, "onServiceDisconnected")
            bingflag = false
        }
    }
    /**  绑定Service  **/

    /**  测试AIDL  **/
    private lateinit var iMyAidlInterface: IMyAidlInterface
    private var bingflag2: Boolean = false
    private var aidlServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bingflag2 = true
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            iMyAidlInterface == null
            bingflag2 = false
        }
    }

    /**  测试AIDL  **/
    override fun onDestroy() {
        super.onDestroy()
        if (bingflag2) {
            unbindService(aidlServiceConnection)
        }
    }


}