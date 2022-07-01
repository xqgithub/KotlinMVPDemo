package com.example.testlibrary.loadsir

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.testlibrary.R
import kotlinx.android.synthetic.main.activity_loadsir.*

/**
 * Date:2022/6/30
 * Time:15:19
 * author:dimple
 * LoadSir框架测试
 */
@Route(path = RouterTag.TestLoadSirActivity)
class TestLoadSirActivity : BaseActivity() {
    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                true, true,
                R.color.appwhite
            )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_loadsir
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_inactivity.setOnClickListener {
            PublicPracticalMethodFromKT.ppmfKT.intentToJump(this@TestLoadSirActivity, NormalActivity::class.java, null, false)
        }
        tv_infragmentnormalctivity.setOnClickListener {
            PublicPracticalMethodFromKT.ppmfKT.intentToJump(this@TestLoadSirActivity, FragmentNormalActivity::class.java, null, false)
        }
    }
}