package com.example.testlibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.constants.RouterTag
import kotlinx.android.synthetic.main.activity_main_test.*


@Route(path = RouterTag.TestMainActivity)
class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        initData()
    }


    /**
     * 初始化数据
     */
    @SuppressLint("ResourceType")
    fun initData() {

        tv_test_main.apply {
            text = "跳转到主程序app中的MainActivity"
        }.setOnClickListener {
            ARouter.getInstance().build(RouterTag.MainActivity).navigation()
//            finish()
        }

        //1.自定义一个TextView1
        val textView = TextView(this@TestMainActivity)
        textView.text = "我是动态添加1"
        textView.textSize = this.resources.getDimension(R.dimen.dimen_10x)
        textView.setTextColor(ContextCompat.getColor(this@TestMainActivity, R.color.black))
        textView.setBackgroundColor(ContextCompat.getColor(this@TestMainActivity, R.color.full_red))
        textView.gravity = Gravity.LEFT
        val vlp = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        //自定义控件id
        textView.id = 99999999
        textView.layoutParams = vlp
        //2.自定义一个TextView2
        val textView2 = TextView(this@TestMainActivity)
        textView2.text = "你个小雀雀"
        textView2.textSize = this.resources.getDimension(R.dimen.dimen_10x)
        textView2.setTextColor(ContextCompat.getColor(this@TestMainActivity, R.color.black))
        textView2.setBackgroundColor(ContextCompat.getColor(this@TestMainActivity, R.color.full_red))
        textView2.gravity = Gravity.CENTER
        val vlp2 = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        //添加控件位置规则
        vlp2.addRule(RelativeLayout.RIGHT_OF, textView.id)

        textView2.layoutParams = vlp2

        //添加控件
        rl_test_main.addView(textView)
        rl_test_main.addView(textView2)


    }

    override fun onDestroy() {
        super.onDestroy()
    }


}