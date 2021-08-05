package com.example.kotlinmvpdemo.mvp.ui.activities

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.RxViewUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerTestPictureSelectorMainComponet
import com.example.kotlinmvpdemo.di.modules.TestPictureSelectorMainModule
import com.example.kotlinmvpdemo.mvp.views.TestPictureSelectorMainView

@Route(path = RouterTag.TestPictureSelectorMainActivity)
class TestPictureSelectorMainActivity : BaseActivity(), TestPictureSelectorMainView,
    RxViewUtils.Action1<View> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_picture_selector_main)
    }

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerTestPictureSelectorMainComponet.builder()
            .myAppComponet(myAppComponet)
            .testPictureSelectorMainModule(TestPictureSelectorMainModule(this))
            .build()
            .inject(this)
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            com.example.testlibrary.R.color.full_red
        )
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_test_picture_selector_main
    }


    override fun onRxViewClick(t: View?) {
    }

}
