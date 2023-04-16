package com.example.testlibrary

import android.content.*
import android.database.Cursor
import android.net.Uri
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
import com.example.testlibrary.contentprovider.People
import com.example.testlibrary.service.MyServiceBind
import com.example.testlibrary.service.MyServiceUnBind
import com.example.testlibrary.service.TestAIDLService
import kotlinx.android.synthetic.main.activity_testservice_startupmode.*

/**
 * Date:2023/4/14
 * Time:15:40
 * author:joker
 * 测试 Service 启动模式和生命周期
 * 测试 AIDL功能
 * 测试 ContentProvider
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

        tvProviderAdd.clickWithTrigger(500) {
            ContentValues().apply {
                put(People.KEY_NAME, "路飞")
                put(People.KEY_AGE, 18)
                put(People.KEY_HEIGHT, 180.7f)
                val _uri = contentResolver.insert(People.CONTENT_URI, this)
                LogUtils.iTag(GLOBAL_TAG, "添加成功 uri = $_uri")
            }
        }

        tvProviderQuery.clickWithTrigger(500) {
            val cursor = contentResolver.query(
                People.CONTENT_URI, arrayOf(People.KEY_ID, People.KEY_NAME, People.KEY_AGE, People.KEY_HEIGHT),
                null, null, null
            )
            if (StringUtils.isBlank(cursor)) {
                LogUtils.iTag(GLOBAL_TAG, "数据库中没有数据")
                return@clickWithTrigger
            }
            LogUtils.iTag(GLOBAL_TAG, "数据库中有${cursor?.count}条记录")
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        LogUtils.iTag(
                            GLOBAL_TAG, "ID = ${it.getString(cursor.getColumnIndex(People.KEY_ID))}",
                            "姓名 = ${it.getString(cursor.getColumnIndex(People.KEY_NAME))}",
                            "年龄 = ${it.getInt(cursor.getColumnIndex(People.KEY_AGE))}",
                            "身高 = ${it.getFloat(cursor.getColumnIndex(People.KEY_HEIGHT))}"
                        )
                    } while (it.moveToNext())
                }
            }
        }

        tvProviderUpdate.clickWithTrigger(500) {
            ContentValues().apply {
                put(People.KEY_NAME, "索隆")
                put(People.KEY_AGE, 19)
                put(People.KEY_HEIGHT, 190.1f)
                val uri = Uri.parse(People.CONTENT_URI_STRING + "/" + "1")
                val result = contentResolver.update(uri, this, null, null)
                if (result > 0) {
                    LogUtils.iTag(GLOBAL_TAG, "更新成功")
                } else {
                    LogUtils.iTag(GLOBAL_TAG, "更新失败")
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