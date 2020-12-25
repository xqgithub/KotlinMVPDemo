package com.example.baselibrary.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.di.componets.MyAppComponet


/**
 * activity 的 基类
 */
abstract class BaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1.禁止横竖屏切换
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //2.关闭屏幕顶部的标题
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //3.初始化Component组件
        setupComponent(MyApplication.myapplication.getMyAppComponet())
        //4.在加载布局之前做的时候
        onBeforeSetContentLayout()
        //5.加载xml布局
        setContentView(getLayoutId())
    }

    /**
     * 子类实现该方法 初始化Component组件
     *
     * @param myAppComponet
     */
    protected abstract fun setupComponent(myAppComponet: MyAppComponet)


    /**
     * 在设置视图内容之前
     * 需要做什么操作可以写在该方法中
     */
    protected abstract fun onBeforeSetContentLayout()


    /**
     * 得到xml布局文件的id
     */
    protected abstract fun getLayoutId(): Int


}