package com.example.testlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.wynsbin.vciv.VerificationCodeInputView
import example.com.testkotlin.haha.utils.showLongToastSafe
import kotlinx.android.synthetic.main.activity_custominputbox.*

/**
 * Date:2021/5/6
 * Time:10:19
 * author:joker
 * 自定义验证码输入框样式
 */
@Route(path = RouterTag.TestCustomInputBoxActivity)
class TestCustomInputBoxActivity : BaseActivity() {

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
        return R.layout.activity_custominputbox
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    /**
     * 初始化数据
     */
    fun initData() {
        /**
         * 输入框属性
         * vciv_et_number：输入框的数量,默认是4位
         * vciv_et_inputType：输入框输入类型
         * 1.number:数字模式
         * 2.numberPassword:数字密码模式
         * 3.text:字符模式
         * 4.textPassword:字符密码模式
         *
         * vciv_et_width：输入框的宽度
         * vciv_et_height：输入框的高度
         * vciv_et_text_color：输入框文字颜色
         * vciv_et_text_size：输入框文字大小
         * vciv_et_spacing：输入框间距，不输入则代表平分
         * vciv_et_background：输入框背景色
         * vciv_et_foucs_background：输入框焦点背景色，不输入代表不设置
         * vciv_et_cursor_width：输入框焦点宽度
         * vciv_et_cursor_height：输入框焦点高度
         * vciv_et_cursor_color：输入框焦点颜色
         * vciv_et_underline_height：输入框下划线高度
         * vciv_et_underline_default_color：输入框无焦点的下划线颜色
         * vciv_et_underline_focus_color：输入框有焦点的下划线颜色
         * vciv_et_underline_show：输入框下划线是否展示
         */
        //验证码输入框监听
        et_vciv.setOnInputListener(object : VerificationCodeInputView.OnInputListener {

            override fun onInput() {
                LogUtils.i(ConfigConstants.TAG_ALL, "onInput =-= ")
            }

            override fun onComplete(code: String?) {
                showLongToastSafe("onComplete：${code}")
            }
        })
    }
}