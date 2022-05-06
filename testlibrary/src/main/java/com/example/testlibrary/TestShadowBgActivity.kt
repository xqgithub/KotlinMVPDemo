package com.example.testlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.activity_shadowbg.*

/**
 * Date:2022/5/6
 * Time:10:25
 * author:dimple
 */
@Route(path = RouterTag.TestShadowBgActivity)
class TestShadowBgActivity : BaseActivity() {

    override fun setupComponent(myAppComponet: MyAppComponet) {

    }

    override fun onBeforeSetContentLayout() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_shadowbg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}