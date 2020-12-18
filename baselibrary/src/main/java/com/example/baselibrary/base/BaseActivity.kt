package com.example.baselibrary.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.di.componets.MyAppComponet


/**
 * activity 的 基类
 */
abstract class BaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1.初始化Component组件
        setupComponent(MyApplication.myapplication.getMyAppComponet())
        //2.加载xml布局
        setContentView(getLayoutId())
    }

    /**
     * 子类实现该方法 初始化Component组件
     *
     * @param myAppComponet
     */
    protected abstract fun setupComponent(myAppComponet: MyAppComponet)

    /**
     * 得到xml布局文件的id
     */
    protected abstract fun getLayoutId(): Int

}