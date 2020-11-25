package com.example.kotlinmvpdemo.mvp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.example.kotlinmvpdemo.mvp.ui.adapter.MainListAdapter
import com.example.kotlinmvpdemo.mvp.views.MainView
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
        //1.初始化recyerview
        val linearlayoutmanager = LinearLayoutManager(this@MainActivity)
        linearlayoutmanager.orientation = RecyclerView.VERTICAL
        val mainlistadapter = MainListAdapter(this@MainActivity, items)
        rv_main.layoutManager = linearlayoutmanager
        rv_main.adapter = mainlistadapter
        //2.Item的点击事件
        mainlistadapter.setOnItemClickListener(object : MainListAdapter.OnItemClickListener {
            override fun setItemOnClick(position: Int, imageView: ImageView) {
                when (position) {
                    0 -> presenter.getTestData(this@MainActivity)
                    1 -> presenter.getTestData2(this@MainActivity)
                    2 -> presenter.getTestData3(this@MainActivity)
                    3 -> presenter.getTestData4(this@MainActivity)
                    4 -> presenter.getTestData5(this@MainActivity)
                    5 -> {
                        test_sp = "其实我是索隆"
                        LogUtils.i("SP_TEST_USER文件中haha字段 =-= $test_sp")
                    }
                    6 -> {
                        presenter.deleteDatas()
                        presenter.insertData()
                        presenter.insertData2()
                    }
                    7 -> {
                        var picPath =
                            "https://n.sinaimg.cn/sinacn20200204ac/551/w816h535/20200204/cc25-inzcrxs3080005.png"
                        imageView.visibility = View.VISIBLE
                        GlideUtils.getInstance().loadUrlToImagaViewActivity(
                            this@MainActivity,
                            picPath,
                            imageView,
                            ScreenUtils.dip2px(this@MainActivity, resources.getDimension(R.dimen.deimen_70x)),
                            ScreenUtils.dip2px(this@MainActivity, resources.getDimension(R.dimen.deimen_70x)),
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher
                        )
                    }
                    8 -> intentToJump(
                        this@MainActivity,
                        TestProductFlavorsActivity::class.java,
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
                    9 -> testEventBus()
                    10 -> presenter.testBuilder()
                    11 -> presenter.testFactory()
                    12 -> presenter.testSimpleFactory()
                    13 -> presenter.testAbstractFactory()
                    14 -> presenter.testCloneMode()
                    15 -> presenter.testStragety()
                    16 -> presenter.testState()
                    17 -> presenter.testResponsibility()
                    18 -> presenter.testObserver()
                    19 -> presenter.testTemplate()
                    20 -> presenter.testIterator()
                }
            }
        })
    }

    /**
     * 先注册EventBus，再跳转到TestProductFlavorsActivity1
     */
    fun testEventBus() {
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

    /**
     *测试数据
     */
    val items = listOf<String>(
        "接口请求测试",
        "接口请求,无条件轮询，间隔一段时间就请求一次",
        "接口请求,有条件轮询",
        "网络请求嵌套回调",
        "网络请求出错重连",
        "测试SP文件数据",
        "User表插入数据",
        "加载网络图片",
        "跳转到TestProductFlavorsActivity页面",
        "EventBus",
        "设计模式---建造者模式",
        "设计模式---工厂方法模式",
        "设计模式---简单工厂模式",
        "设计模式---抽象工厂模式",
        "设计模式---原型模式",
        "设计模式---策略模式",
        "设计模式---状态模式",
        "设计模式---责任链模式",
        "设计模式---观察者模式",
        "设计模式---模板方法模式",
        "设计模式---迭代器模式"
    )


    override fun onDestroy() {
        super.onDestroy()
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }


}
