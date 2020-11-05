package com.example.kotlinmvpdemo.mvp.ui.activities

import android.content.Intent
import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.EventTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.entity.MessageEvent
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.SPreferenceUtils
import com.example.baselibrary.utils.ScreenUtils
import com.example.baselibrary.utils.glideutils.GlideUtils
import com.example.baselibrary.utils.intentToJump
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerMainComponet
import com.example.kotlinmvpdemo.di.modules.MainModule
import com.example.kotlinmvpdemo.mvp.presenters.MainPresenter
import com.example.kotlinmvpdemo.mvp.views.MainView
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

        tv_test7.setOnClickListener {
            presenter.deleteDatas()
            presenter.insertData()
            presenter.insertData2()
        }

        tv_test8.setOnClickListener {
            var picPath = "https://n.sinaimg.cn/sinacn20200204ac/551/w816h535/20200204/cc25-inzcrxs3080005.png"
            GlideUtils.getInstance().loadUrlToImagaViewActivity(
                this@MainActivity,
                picPath,
                iv_test8,
                ScreenUtils.dip2px(this@MainActivity, resources.getDimension(R.dimen.deimen_60x)),
                ScreenUtils.dip2px(this@MainActivity, resources.getDimension(R.dimen.deimen_60x)),
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher
            )
        }

        tv_test9.setOnClickListener {
            intentToJump(
                this@MainActivity,
                TestProductFlavorsActivity::class.java,
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
        }

        tv_test10.setOnClickListener {
            //1.注册EventBus
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
            //2.跳转到 TestProductFlavorsActivity 页面
            intentToJump(
                this@MainActivity,
                TestProductFlavorsActivity::class.java,
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
        }
    }

    /**
     * EventBus 测试消息2
     * @param ThreadMode.POSTING:默认的线程模式，在哪个线程发送事件就在对应线程处理事件，避免了线程切换，效率高
     * @param ThreadMode.MAIN:如在主线程（UI线程）发送事件，则直接在主线程处理事件；如果在子线程发送事件，则先将事件入队列，然后通过 Handler 切换到主线程，依次处理事件
     * @param ThreadMode.MAIN_ORDERED:无论在哪个线程发送事件，都将事件加入到队列中，然后通过Handler切换到主线程，依次处理事件
     * @param ThreadMode.BACKGROUND:与ThreadMode.MAIN相反，如果在子线程发送事件，则直接在子线程处理事件；如果在主线程上发送事件，则先将事件入队列，然后通过线程池处理事件
     * @param ThreadMode.ASYNC:与ThreadMode.MAIN_ORDERED相反，无论在哪个线程发送事件，都将事件加入到队列中，然后通过线程池执行事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    fun EventBusTestMessage(messageevent: MessageEvent) {
        if (EventTag.event_test == messageevent.message_type) {
            LogUtils.i("收到测试消息 =-= ${messageevent.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }
}
