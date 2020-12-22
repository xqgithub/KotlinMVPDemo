package com.example.kotlinmvpdemo.mvp.ui.fragments

import android.os.Bundle
import com.example.baselibrary.base.BaseFragment
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerTwoFragmentComponet
import com.example.kotlinmvpdemo.di.modules.TwoFragmentModule
import com.example.kotlinmvpdemo.mvp.views.TwoFragmentView

class TwoFragment : BaseFragment(), TwoFragmentView {

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerTwoFragmentComponet.builder()
            .myAppComponet(myAppComponet)
            .twoFragmentModule(TwoFragmentModule(this))
            .build()
            .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_two
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.i(ConfigConstants.TAG_ALL, "初始化了---TwoFragment")
    }

    override fun onPause() {
        super.onPause()
        isLoadData = true
    }


    override fun lazyLoad() {
        LogUtils.i(ConfigConstants.TAG_ALL, "我要开始加载数据了---TwoFragment")
    }
}
