package com.example.kotlinmvpdemo.mvp.ui.activities

import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.SPreferenceUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerMainComponet
import com.example.kotlinmvpdemo.di.modules.MainModule
import com.example.kotlinmvpdemo.mvp.presenters.MainPresenter
import com.example.kotlinmvpdemo.mvp.views.MainView
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    //如果没有，那么获取默认值 我是测试数据路飞
    var test_sp: String by SPreferenceUtils(
        this@MainActivity,
        ConfigConstants.SP_TEST_USER,
        "haha",
        "我是测试数据路飞"
    )

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerMainComponet.builder()
            .myAppComponet(myAppComponet)
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init()
    }

    override fun init() {
        LogUtils.i("=-= 我要开始初始化了")
        tv_test.setOnClickListener {
            showShortToastSafe("你好我叫hello world")
            presenter.getTestData(this@MainActivity)
        }
        tv_test2.setOnClickListener {
            presenter.getTestData2(this@MainActivity)
        }
        tv_test3.setOnClickListener {
            presenter.getTestData3(this@MainActivity)
        }
        tv_test4.setOnClickListener {
            presenter.getTestData4(this@MainActivity)
        }
        tv_test5.setOnClickListener {
            presenter.getTestData5(this@MainActivity)
        }
        tv_test6.setOnClickListener {
            //1.创建文件sp_test_user.xml文件，保存数值
//            test_sp = "其实我是索隆"
            LogUtils.i("SP_TEST_USER文件中haha字段 =-= $test_sp")
        }
    }
}
