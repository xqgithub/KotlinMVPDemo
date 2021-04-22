package com.example.testlibrary

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_bezier.*
import kotlinx.android.synthetic.main.activity_lambda.*
import kotlinx.android.synthetic.main.activity_svg.*

/**
 * Date:2021/4/22
 * Time:10:33
 * author:joker
 * 贝塞尔曲线类
 */
@Route(path = RouterTag.TestBezierActivity)
class TestBezierActivity : BaseActivity() {

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            R.color.full_red
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bezier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    /**
     * 初始化数据
     */
    fun initData() {
        tv_bezier.setOnClickListener {
            try {
                when (et_bezier.text.toString().trim()) {
                    "1" -> {
                        sob_view.visibility = View.VISIBLE
                    }
                    else -> {
                        showShortToastSafe("序号错误，请检查")
                        return@setOnClickListener
                    }
                }
                et_bezier!!.clearFocus()
                (it as TextView).text = "运行了序号为${et_bezier.text}的方法"
            } catch (e: Exception) {
                LogUtils.e(e.message)
            }
        }
    }

}