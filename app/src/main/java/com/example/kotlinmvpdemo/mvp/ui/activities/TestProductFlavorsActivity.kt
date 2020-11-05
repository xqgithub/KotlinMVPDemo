package com.example.kotlinmvpdemo.mvp.ui.activities

import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.EventTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.entity.MessageEvent
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerProductFlavorsComponet
import com.example.kotlinmvpdemo.di.modules.ProductFlavorsModule
import com.example.kotlinmvpdemo.mvp.views.ProductFlavorsView
import kotlinx.android.synthetic.main.activity_productflavors.*
import org.greenrobot.eventbus.EventBus

/**
 * 测试多版本差异化
 */
class TestProductFlavorsActivity : BaseActivity(), ProductFlavorsView {


    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerProductFlavorsComponet.builder()
            .myAppComponet(myAppComponet)
            .productFlavorsModule(ProductFlavorsModule(this))
            .build()
            .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_productflavors
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获得版本的package_id
        val package_id = PublicPracticalMethodFromJAVA.getInstance()
            .getMetaDataValue(this@TestProductFlavorsActivity, ConfigConstants.PACKAGE_ID)
        if (package_id == ConfigConstants.ceshi) {
            tv_name.text = getString(R.string.productflavors_ceshi)
        } else if (package_id == ConfigConstants.shengchan) {
            tv_name.text = getString(R.string.productflavors_shengchan)
        }


        //发送eventbus测试消息
        tv_eventbus.setOnClickListener {
            var messageevent: MessageEvent = MessageEvent()
            messageevent.message_type = EventTag.event_test
            messageevent.message = "测试eventbus消息"
            EventBus.getDefault().post(messageevent)
            finish()
        }
    }
}