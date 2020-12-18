package com.example.kotlinmvpdemo.mvp.ui.fragments

import android.os.Bundle
import com.example.baselibrary.base.BaseFragment
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerOneFragmentComponet
import com.example.kotlinmvpdemo.di.modules.OneFragmentModule
import com.example.kotlinmvpdemo.mvp.views.FragmentOneView

/**
 * Fragment 1号页面
 */
class OneFragment : BaseFragment(), FragmentOneView {

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
        LogUtils.i(ConfigConstants.TAG_ALL, "OneFragment 初始化了")
    }
}