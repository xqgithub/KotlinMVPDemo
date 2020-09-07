package com.example.baselibrary.application

import androidx.multidex.MultiDexApplication
import com.example.baselibrary.di.componets.DaggerMyAppComponet
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.di.modules.MyAppModule
import com.example.baselibrary.utils.CrashHandler
import com.example.baselibrary.utils.LogUtils
import com.facebook.stetho.Stetho

/**
 * 自定义Application
 */
class MyApplication : MultiDexApplication() {

    private lateinit var myappcomponet: MyAppComponet

    companion object {
        lateinit var myapplication: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        myapplication = this
        initConfig()
        setupGraph()


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
        //1.日志类加载初始化
        LogUtils.Builder()
            .setLogSwitch(true)//设置log总开关，默认开
            //.setGlobalTag("CMJ")// 设置log全局标签，默认为空
            .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
            .setBorderSwitch(false)// 输出日志是否带边框开关，默认开
            .setLogFilter(LogUtils.V)// log过滤器，和logcat过滤器同理，默认Verbose
        //2.加载全部异常捕获
        val crashhandler = CrashHandler.getInstance()
        crashhandler.init(this)
        //3.初始化Stetho出正式包的时候，建议屏蔽掉
        Stetho.initializeWithDefaults(this)
    }

}