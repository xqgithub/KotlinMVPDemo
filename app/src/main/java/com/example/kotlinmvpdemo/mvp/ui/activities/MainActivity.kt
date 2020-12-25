package com.example.kotlinmvpdemo.mvp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.EventTag
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.entity.MessageEvent
import com.example.baselibrary.mvp.entity.Person
import com.example.baselibrary.utils.*
import com.example.baselibrary.utils.glideutils.GlideUtils
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

@Route(path = RouterTag.MainActivity)
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

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                false, true,
                R.color.full_red
            )
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
        //获得手机屏幕信息
        PublicPracticalMethodFromJAVA.getInstance().getPhoneScreenInfo(this@MainActivity)


        //1.初始化recyerview
        val linearlayoutmanager = LinearLayoutManager(this@MainActivity)
        linearlayoutmanager.orientation = RecyclerView.VERTICAL
        val mainlistadapter = MainListAdapter(this@MainActivity, items)
        rv_main.layoutManager = linearlayoutmanager
        rv_main.adapter = mainlistadapter
        //2.Item的点击事件
        mainlistadapter.setOnItemClickListener(object : MainListAdapter.OnItemClickListener {
            override fun setItemOnClick(position: Int, imageView: ImageView, picPath: String) {
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
//                        PublicPracticalMethodFromJAVA.getInstance().smallWidth()
                    }
                    7 -> {
                        if (StringUtils.compared(picPath, imageView.tag)) {
                            GlideUtils.getInstance().loadUrlToImagaViewActivity(
                                this@MainActivity,
                                imageView.tag.toString(),
                                imageView,
                                ScreenUtils.dip2px(this@MainActivity, resources.getDimension(R.dimen.dimen_70x)),
                                ScreenUtils.dip2px(this@MainActivity, resources.getDimension(R.dimen.dimen_70x)),
                                R.mipmap.ic_launcher,
                                R.mipmap.ic_launcher
                            )
                        }
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
                    21 -> presenter.testMemo()
                    22 -> presenter.testNotification(this@MainActivity)
                    23 -> presenter.testVisitor()
                    24 -> presenter.testIntermediary()
                    25 -> presenter.testInterpreter()
                    26 -> {
                        //1.测试withObject  传递list集合的值
                        val mutableListOf = mutableListOf<Person>()
                        mutableListOf.add(Person().apply {
                            this.age = 24
                            this.name = "路飞"
                        })
                        mutableListOf.add(Person().apply {
                            this.age = 25
                            this.name = "索隆"
                        })

                        //2.测试withObject 传递map集合的值
                        val mutablemap = mutableMapOf<String, Person>()
                        mutablemap.put("person1", Person().apply {
                            this.age = 23
                            this.name = "娜美"
                        })


                        ARouter.getInstance().build(RouterTag.TestProductFlavorsActivity)
                            .withString("key1", "我叫路飞")
                            .withInt("key2", 100)
                            .withLong("key3", 200L)
                            .withBoolean("key4", true)
                            .withObject("key5", Person().apply {
                                this.age = 24
                                this.name = "lufei"
                            })
                            .withObject("key6", mutableListOf)
                            .withObject("key7", mutablemap)
                            .withTransition(R.anim.slide_in_right, 0)
//                            .navigation()
                            .navigation(this@MainActivity, object : NavCallback() {

                                override fun onFound(postcard: Postcard?) {
                                    LogUtils.i(ConfigConstants.TAG_ALL, "拦截找到了")
                                }

                                /**
                                 * 单独降解处理
                                 * 跳转失败的时候会回调
                                 */
                                override fun onLost(postcard: Postcard?) {
                                    LogUtils.i(ConfigConstants.TAG_ALL, "拦截没有找到了")
                                }

                                override fun onInterrupt(postcard: Postcard?) {
                                    LogUtils.i(ConfigConstants.TAG_ALL, "拦截器拦截了,查处了问题需要做其他处理")
                                }

                                override fun onArrival(postcard: Postcard?) {
                                    LogUtils.i(
                                        ConfigConstants.TAG_ALL, "${postcard!!.extras.getString("interceptor_key1")
                                        }", "拦截离开了"
                                    )
                                }
                            })
                    }
                    27 -> ARouter.getInstance().build(RouterTag.TestMainActivity).navigation()
                    28 -> ARouter.getInstance().build(RouterTag.CoroutineActivity).navigation()
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
        "设计模式---迭代器模式",
        "设计模式---备忘录模式",
        "弹出通知栏",
        "设计模式---访问者模式",
        "设计模式---中介者模式",
        "设计模式---解释器模式",
        "ARouter跳转测试---同一个module",
        "ARouter跳转测试---不同的module",
        "协程Coroutine测试"
    )

    override fun onDestroy() {
        super.onDestroy()
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }


}
