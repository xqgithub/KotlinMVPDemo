package com.example.kotlinmvpdemo.mvp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.entity.Person
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.NotificationHelperUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerProductFlavorsComponet
import com.example.kotlinmvpdemo.di.modules.ProductFlavorsModule
import com.example.kotlinmvpdemo.mvp.views.ProductFlavorsView
import kotlinx.android.synthetic.main.activity_productflavors.*

/**
 * 测试多版本差异化
 */
@Route(path = RouterTag.TestProductFlavorsActivity)
class TestProductFlavorsActivity : BaseActivity(), ProductFlavorsView {

    //通过ARouter跳转传过来的值
    @JvmField
    @Autowired(name = "key1")
    var key1: String = ""

    @JvmField
    @Autowired(name = "key2")
    var key2: Int = 0

    @JvmField
    @Autowired(name = "key3")
    var key3: Long = 0L

    @JvmField
    @Autowired(name = "key4")
    var key4: Boolean = false

    @JvmField
    @Autowired(name = "key5")
    var key5: Person? = null

    @JvmField
    @Autowired(name = "key6")
    var key6: MutableList<Person>? = null

    @JvmField
    @Autowired(name = "key7")
    var key7: MutableMap<String, Person>? = null


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

    @SuppressLint("NewApi")
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
            //            var messageevent: MessageEvent = MessageEvent()
//            messageevent.message_type = EventTag.event_test
//            messageevent.message = "测试eventbus消息"
//            EventBus.getDefault().post(messageevent)
//            finish()

            if (NotificationHelperUtils.getInstance().isNotifacationEnabled(this@TestProductFlavorsActivity)) {
                //应为自定义中有点击事件，所有先注册广播
//                val receiver = NotificationBrodcaseReceiver()
//                NotificationHelperUtils.getInstance().registerNotificationBrodcaseRecever(
//                    this@TestProductFlavorsActivity, receiver, ConfigConstants.notifacatio_close
//                )


                NotificationHelperUtils.getInstance().sendNotification2(
                    this@TestProductFlavorsActivity, TestProductFlavorsActivity::class.java,
                    111, "海贼王", "我要成为海贼王的男人"
                )
            } else {
                NotificationHelperUtils.getInstance().openPermission(this@TestProductFlavorsActivity)
            }
        }


        /**
         * 使用@Autowired 注解时, 必须要在对应的Activity中 调用 ARouter.getInstance().inject(this);
         * Kotlin 代码编写的项目 在 @Autowired 标注的变量上, 还需要添加注解 @JvmField
         */
        ARouter.getInstance().inject(this)
        //通过ARouter跳转穿过来的值
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "key1 = ${key1}",
            "key2=${key2}",
            "key3=${key3}",
            "key4=${key4}",
            "key5.age=${key5!!.age},key5.name=${key5!!.name}"
        )

        for (k in key6!!) {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "key6.age=${k.age},key6.name=${k.name}"
            )
        }

        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "key7.age=${key7!!["person1"]!!.age},key7.name=${key7!!["person1"]!!.name}"
        )
    }
}