package com.example.testlibrary

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.view.ObjectAnimatorCustomView
import com.example.baselibrary.mvp.view.typeevaluator.ColorEvaluator
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_propertyanimation.*


/**
 * Date:2021/4/27
 * Time:14:57
 * author:joker
 * 属性动画和视图动画
 */
@Route(path = RouterTag.TestPropertyAnimationActivity)
class TestPropertyAnimationActivity : BaseActivity() {

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
        return R.layout.activity_propertyanimation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        tv_propertyanimation.setOnClickListener {
            when (et_propertyanimation.text.toString()) {
                "1" -> {
                    btn.visibility = View.VISIBLE
                    vacv_view.visibility = View.GONE
                    btn2.visibility = View.GONE
                    oacv_view.visibility = View.GONE
                    playValueAnimator()
                }
                "2" -> {
                    btn.visibility = View.GONE
                    vacv_view.visibility = View.VISIBLE
                    btn2.visibility = View.GONE
                    oacv_view.visibility = View.GONE
                }
                "3" -> {
                    btn.visibility = View.GONE
                    vacv_view.visibility = View.GONE
                    btn2.visibility = View.VISIBLE
                    oacv_view.visibility = View.GONE
                    playObjectAnimator()
                }
                "4" -> {
                    btn.visibility = View.GONE
                    vacv_view.visibility = View.GONE
                    btn2.visibility = View.GONE
                    oacv_view.visibility = View.VISIBLE
                    playObjectAnimator2()
                }
                else -> showShortToastSafe("序号错误，请检查")
            }
        }
    }


    /**
     * 属性动画---ValueAnimator：通过不断控制 值 的变化，再不断 手动 赋给对象的属性，从而实现动画效果
     * ValueAnimator.ofInt
     * ofInt(int... values):允许传入多个值
     * 1.输入一个的情况（如a）：从0过渡到a
     * 2.输入多个的情况（如a，b，c）：先从a平滑过渡到b，再从b平滑过渡到C
     */
    private fun playValueAnimator() {
        //1.设置属性数值的初始值 & 结束值
        var valueAnimator = ValueAnimator.ofInt(resources.getDimensionPixelSize(R.dimen.dimen_80x), resources.getDimensionPixelSize(R.dimen.dimen_150x))
        //2.设置动画属性
        valueAnimator.duration = 3000//动画时长
        valueAnimator.startDelay = 0//动画延迟播放时间
        valueAnimator.repeatCount = 0//动画播放次数=重放次数+1, infinite动画无限重复
        valueAnimator.repeatMode = ValueAnimator.RESTART//设置重复播放动画模式：RESTART 正序重放;REVERSE:倒序回放
        //3.设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener {
            var currentValue = it.animatedValue//获得每次变化后的属性值
            LogUtils.i(ConfigConstants.TAG_ALL, "currentValue =-= $currentValue")
            btn.layoutParams.width = currentValue as Int//每次变化后的值赋值给按钮的宽度，这样就实现了按钮的动态变化

            //4.刷新视图，即重新绘制，从而实现动画效果
            btn.requestLayout()
        }
        //启动动画
        valueAnimator.start()
    }


    /**
     * 属性动画---ObjectAnimator
     * ObjectAnimator.ofFloat(Object object, String property, float ....values)
     * 1.Object object：需要操作的对象
     *
     * 2.String property：需要操作的对象的属性
     * (1)Alpha:控制View的透明度
     * (2)TranslationX:控制X方向的位移
     * (3)TranslationY:控制Y方向的位移
     * (4)ScaleX:控制X方向的缩放倍数
     * (5)ScaleY:控制Y方向的缩放倍数
     * (6)Rotation:控制以屏幕方向为轴的旋转度数
     * (7)RotationX:控制以X轴为轴的旋转度数
     * (8)RotationY:控制以Y轴为轴的旋转度数
     * 赋值作用：让ObjectAnimator类根据传入的属性名 去寻找 该对象对应属性名的 set（） & get（）方法，从而进行对象属性值的赋值
     *
     * 3.float ....values：动画初始值 & 结束值（不固定长度）,若是两个参数a,b，则动画效果则是从属性的a值到b值,若是三个参数a,b,c，则则动画效果则是从属性的a值到b值再到c值
     *
     */
    private fun playObjectAnimator() {
        //透明度动画
        var animator = ObjectAnimator.ofFloat(btn2, "alpha", 1f, 0f, 1f)
        animator.duration = 3000
        animator.repeatCount = 0
        animator.repeatMode = ObjectAnimator.RESTART
        animator.start()

        //翻转动画
        var animator2 = ObjectAnimator.ofFloat(btn2, "rotation", 0f, 360f)
        animator2.duration = 3000
        animator2.repeatCount = 0
        animator2.repeatMode = ObjectAnimator.RESTART
        animator2.start()

        //平移动画
        var curTranslationX = btn2.translationX
        var animator3 = ObjectAnimator.ofFloat(btn2, "translationX", curTranslationX, 400f, curTranslationX)
        animator3.duration = 3000
        animator3.repeatCount = 0
        animator3.repeatMode = ObjectAnimator.RESTART
        animator3.start()

        //缩放动画
        var animator4 = ObjectAnimator.ofFloat(btn2, "scaleX", 1f, 3f, 1f)
        animator4.duration = 3000
        animator4.repeatCount = 0
        animator4.repeatMode = ObjectAnimator.RESTART
        animator4.start()
    }

    /**
     * 自定义属性动画，改变颜色的值
     */
    private fun playObjectAnimator2() {
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofObject(
            oacv_view, "color", ColorEvaluator(),
            "#0000FF", "#FF0000"
        )
        objectAnimator.duration = 5000
        objectAnimator.repeatCount = 0
        objectAnimator.repeatMode = ObjectAnimator.RESTART
        objectAnimator.start()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}