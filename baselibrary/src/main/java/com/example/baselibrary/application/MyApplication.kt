package com.example.baselibrary.application

import android.hardware.Camera
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.BuildConfig
import com.example.baselibrary.di.componets.DaggerMyAppComponet
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.di.modules.MyAppModule
import com.example.baselibrary.mvp.view.loadsir.EmptyCallback
import com.example.baselibrary.mvp.view.loadsir.ErrorCallback
import com.example.baselibrary.mvp.view.loadsir.LoadingCallback
import com.example.baselibrary.utils.CrashHandler
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.ScreenTools
import com.example.baselibrary.utils.lifecycle.ActivityState
import com.example.baselibrary.utils.lifecycle.MyActivityLifecycleCallbacks
import com.facebook.stetho.Stetho
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir

/**
 * 自定义Application
 */
open class MyApplication : MultiDexApplication() {


    private lateinit var myappcomponet: MyAppComponet

    @JvmField
    var lifecycleCallbacks: MyActivityLifecycleCallbacks = MyActivityLifecycleCallbacks()

    companion object {
        lateinit var myapplication: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        myapplication = this
        initConfig()
        setupGraph()
        //注册Activity生命周期监听回调
        initActivityLifecycle()
    }

    /**
     * 初始化 MyAppComponet
     */
    private fun setupGraph() {
        myappcomponet = DaggerMyAppComponet
            .builder()
            .myAppModule(MyAppModule(this))
            .build()
        myappcomponet.inject(this)
    }

    /**
     * 获得MyAppComponet实例
     */
    fun getMyAppComponet(): MyAppComponet {
        return myappcomponet
    }

    /**
     * 初始化配置
     */
    private fun initConfig() {
        //1.初始化AutoSize
        ScreenTools.getInstance().initAutoSize(this, this)

        //2.日志类加载初始化
        LogUtils.Builder()
            .setLogSwitch(true)//设置log总开关，默认开
            //.setGlobalTag("CMJ")// 设置log全局标签，默认为空
            .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
            .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
            .setLogFilter(LogUtils.V)// log过滤器，和logcat过滤器同理，默认Verbose
        //3.加载全部异常捕获
        val crashhandler = CrashHandler.getInstance()
        crashhandler.init(this)
        //4.初始化Stetho出正式包的时候，建议屏蔽掉
        Stetho.initializeWithDefaults(this)
        //5.ARouter初始化
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
        //6.LoadSir框架
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
    }


    /**
     * 初始化ActivityLifecycle
     */
    fun initActivityLifecycle() {
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

}