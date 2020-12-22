package com.example.kotlinmvpdemo.mvp.ui.fragments

import android.os.Bundle
import com.example.baselibrary.base.BaseFragment
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerOneFragmentComponet
import com.example.kotlinmvpdemo.di.modules.OneFragmentModule
import com.example.kotlinmvpdemo.mvp.views.OneFragmentView

/**
 * Fragment 1号页面
 */
class OneFragment : BaseFragment(), OneFragmentView {

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerOneFragmentComponet.builder()
            .myAppComponet(myAppComponet)
            .oneFragmentModule(OneFragmentModule(this))
            .build()
            .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_one
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.i(ConfigConstants.TAG_ALL, "初始化了---OneFragment")
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        isLoadData = true
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * Fragment 延迟加载数据
     */
    override fun lazyLoad() {
        LogUtils.i(ConfigConstants.TAG_ALL, "我要开始加载数据了---OneFragment")
    }
}