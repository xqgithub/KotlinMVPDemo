package com.example.kotlinmvpdemo.mvp.ui.activities

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerMainComponet
import com.example.kotlinmvpdemo.di.componets.DaggerTestLoopViewComponet
import com.example.kotlinmvpdemo.di.modules.MainModule
import com.example.kotlinmvpdemo.di.modules.TestLoopViewModule
import com.example.kotlinmvpdemo.mvp.presenters.MainPresenter
import com.example.kotlinmvpdemo.mvp.presenters.TestLoopViewPresenter
import com.example.kotlinmvpdemo.mvp.views.TestLoopViewView
import kotlinx.android.synthetic.main.activity_test_loop_view.*
import javax.inject.Inject

/**
 * 测试单项选择滑动框
 */
@Route(path = RouterTag.TestLoopViewActivity)
class TestLoopViewActivity : BaseActivity(), TestLoopViewView {

    @Inject
    lateinit var presenter: TestLoopViewPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_loop_view)
        presenter.initLoopView(loopView)


        //滚动监听
        loopView.setListener {
//            LogUtils.i("选择的是 index $it")
        }

        loopView.setOnItemClickListenter {
            LogUtils.i("选择的是 index $it")
        }


        //设置背景色
//        loopView.setPaintCenterTextBGColor(ContextCompat.getColor(this, R.color.appwhite))
        //设置文字边距
        loopView.setTextLeftPadding(resources.getDimensionPixelSize(R.dimen.dimen_30x))
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                false, true,
                R.color.appwhite
            )
    }


    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerTestLoopViewComponet.builder()
            .myAppComponet(myAppComponet)
            .testLoopViewModule(TestLoopViewModule(this))
            .build()
            .inject(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_test_loop_view
    }
}
