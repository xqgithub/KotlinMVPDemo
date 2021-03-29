package com.example.testlibrary

import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.clickWithTrigger
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.android.synthetic.main.activity_svg.*

/**
 * 1.SVG 动画测试
 * 2.自定义View---Canvas的使用
 */
@Route(path = RouterTag.TestSVGActivity)
class TestSVGActivity : BaseActivity() {


    override fun setupComponent(myAppComponet: MyAppComponet) {

    }

    override fun onBeforeSetContentLayout() {
        //设置状态栏
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this@TestSVGActivity,
            false, false, R.color.full_red
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_svg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        group_svg.visibility = View.GONE


        //手指放大、缩小
//        var gestureViewBinder = GestureViewBinder.bind(this, groupView, targetView)
//        gestureViewBinder.isFullGroup = true

        tv_clearscreen.clickWithTrigger(500) {
//            gestureViewBinder = null
            val branch = et_testcustom.text.toString().toInt()
            when (branch) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                else -> {
                    tcv.methodNums = branch
                }
            }

        }

    }

    /**
     * 加载数据
     */
    private fun initData() {
        //加载动态的SVG动画
        //1.SVG动画vector_drawable_menu.xml放在drawable-anydpi-v21文件夹里面
        //2.SDK_INT在5.0以上运行动画，否则加载drawable中的ic_back
        //3.运行于 4.4的机子时，会报找不到相应资源的错误。所以我们需要在 drawable 文件夹下，建一个相同名字的资源vector_drawable_menu.xml,只是里面的内容不是 animated-vector 作为根标签
        tv_svg3.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SVG动画是在5.0之后推出的
                (iv_svg5.drawable as Animatable).start()
            } else {
                iv_svg5.setImageDrawable(ContextCompat.getDrawable(this@TestSVGActivity, R.drawable.ic_back2))
            }
        }
    }
}