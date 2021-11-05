package com.example.testlibrary.fragments

import android.os.Bundle
import com.example.baselibrary.base.BaseFragment
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.testlibrary.R
import kotlinx.android.synthetic.main.fragment_three.*

class ThreeFragment(var mTitlte: String) : BaseFragment() {
    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_three
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        isLoadData = true
    }


    override fun lazyLoad() {
        tv_fragment_name.text = mTitlte
    }
}
