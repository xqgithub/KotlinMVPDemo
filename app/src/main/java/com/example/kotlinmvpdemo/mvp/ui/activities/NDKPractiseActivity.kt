package com.example.kotlinmvpdemo.mvp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.clickWithTrigger
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerNDKPractiseComponet
import com.example.kotlinmvpdemo.di.modules.NDKPractiseModule
import com.example.kotlinmvpdemo.mvp.views.NDKPractiseView
import com.example.kotlinmvpdemo.ndk.nativelib
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_ndk_practise.*

/**
 * NDK 测试
 */
@Route(path = RouterTag.NDKPractiseActivity)
class NDKPractiseActivity : BaseActivity(), NDKPractiseView {

    private lateinit var nativelib: nativelib

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                true, true,
                R.color.appwhite
            )
    }

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerNDKPractiseComponet.builder()
            .myAppComponet(myAppComponet)
            .nDKPractiseModule(NDKPractiseModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ndk_practise)
        nativelib = nativelib()

        tv_one.clickWithTrigger(500) {
            val branch = et_one.text.toString().toInt()
            when (branch) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                1 -> {
                    testOne()
                }
                2 -> {
                    testTwo()
                }
                else -> showShortToastSafe("序号错误，请检查")
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_ndk_practise
    }

    /**
     * 测试 NDK 是否能实现
     */
    fun testOne() {
        val a = nativelib.sum(3, 4)
        val b = nativelib.stringFromJNI()

        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "a =-= $a",
            "b =-= $b"
        )
    }


    /**
     * 测试 C语言中的 基本数据类型
     */
    fun testTwo() {
        nativelib.testbasictype()
    }
}
