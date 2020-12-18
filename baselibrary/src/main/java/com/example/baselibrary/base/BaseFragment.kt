package com.example.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.di.componets.MyAppComponet

/**
 * Fragment的基类
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //1.初始化Component组件
        setupComponent(MyApplication.myapplication.getMyAppComponet())
        //2.加载xml布局
        val view = inflater.inflate(getLayoutId(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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