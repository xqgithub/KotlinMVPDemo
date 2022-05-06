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
import com.example.baselibrary.mvp.service.TaskRemovedService
import com.example.baselibrary.utils.*
import com.example.baselibrary.utils.glideutils.GlideUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerMainComponet
import com.example.kotlinmvpdemo.di.modules.MainModule
import com.example.kotlinmvpdemo.mvp.presenters.MainPresenter
import com.example.kotlinmvpdemo.mvp.ui.adapter.MainListAdapter
import com.example.kotlinmvpdemo.mvp.views.MainView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
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

        //初始化recyclerview
        initRecyclerview(false)

        //启动服务当应用被移除的时候，做操作
        startTaskRemovedService()

        //测试
//        presenter.testDataConvert()
    }


    /**
     * 初始化recyclerview
     * @param isOpenRefresh 是否开启下拉刷新，上推加载更多功能
     */
    private fun initRecyclerview(isOpenRefresh: Boolean) {
        if (isOpenRefresh) {
            //1.下拉刷新
            srl_main.setRefreshHeader(ClassicsHeader(this@MainActivity))
            //下拉刷新监听
            srl_main.setOnRefreshListener {
                LogUtils.i(ConfigConstants.TAG_ALL, "我被下拉刷新了")

                //数据还原
                tempnum = 0
                items_remaining = items.size
                tempList.clear()
                takeDataFromItems()
                (rv_main.adapter as MainListAdapter).notifyDataSetChanged()

                it.finishRefresh(500)
            }
            //3.上推加载
            srl_main.setRefreshFooter(ClassicsFooter(this@MainActivity))
            //上推加载监听
            srl_main.setOnLoadMoreListener {
                LogUtils.i(ConfigConstants.TAG_ALL, "我被上推加载了")

                takeDataFromItems()

                if (items_remaining > 0) {//表示剩余数据
                    it.finishLoadMore(500)
                } else {
                    it.finishLoadMoreWithNoMoreData()
                }

                (rv_main.adapter as MainListAdapter).notifyDataSetChanged()
            }

            //从测试数据中取数据
            takeDataFromItems()

        } else {
            srl_main.setEnableRefresh(isOpenRefresh)
            srl_main.setEnableLoadMore(isOpenRefresh)
        }

        //4.创建一个layout管理器
        val linearlayoutmanager = LinearLayoutManager(this@MainActivity)
        linearlayoutmanager.orientation = RecyclerView.VERTICAL
        rv_main.layoutManager = linearlayoutmanager

        //5.初始化recyclerview的适配器
        val mainlistadapter = MainListAdapter(this@MainActivity, if (isOpenRefresh) tempList else items)
        rv_main.adapter = mainlistadapter

        //6.数据刷新
        (rv_main.adapter as MainListAdapter).notifyDataSetChanged()

        //7.Item的点击事件
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
                        PublicPracticalMethodFromJAVA.getInstance().smallWidth()
//                        PublicPracticalMethodFromJAVA.getInstance().smallWidth2()


//                        presenter.deleteDatas()
//                        presenter.insertData()
//                        presenter.insertData2()

//                        val nativelib = nativelib()
//                        val a = nativelib.sum(3, 4)
//                        val b = nativelib.stringFromJNI()
//                        LogUtils.i(ConfigConstants.TAG_ALL, "返回的值为：${b + a}")
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
                    8 -> {
//                        intentToJump(
//                            this@MainActivity,
//                            TestProductFlavorsActivity::class.java,
//                            Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        )
                    }
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
                                        ConfigConstants.TAG_ALL, "${
                                            postcard!!.extras.getString("interceptor_key1")
                                        }", "拦截离开了"
                                    )
                                }
                            })
                    }
                    27 -> ARouter.getInstance().build(RouterTag.TestMainActivity).navigation()
                    28 -> ARouter.getInstance().build(RouterTag.CoroutineActivity).navigation()
                    29 -> ARouter.getInstance().build(RouterTag.LambdaActivity).navigation()
                    30 -> ARouter.getInstance().build(RouterTag.TestSVGActivity).navigation()
                    31 -> presenter.testCommand()
                    32 -> ARouter.getInstance().build(RouterTag.Rxjava2UseActivity).navigation()
                    33 -> ARouter.getInstance().build(RouterTag.ReflectionActivity).navigation()
                    34 -> ARouter.getInstance().build(RouterTag.TestScreenRecordActivity).navigation()
                    35 -> ARouter.getInstance().build(RouterTag.TestBezierActivity).navigation()
                    36 -> ARouter.getInstance().build(RouterTag.TestPropertyAnimationActivity).navigation()
                    37 -> ARouter.getInstance().build(RouterTag.TestCustomInputBoxActivity).navigation()
                    38 -> ARouter.getInstance().build(RouterTag.TestCalendarViewActivity).navigation()
                    39 -> ARouter.getInstance().build(RouterTag.TestPictureSelectorMainActivity).withTransition(R.anim.slide_in_right, 0).navigation(this@MainActivity)
                    40 -> ARouter.getInstance().build(RouterTag.TestLoopViewActivity).navigation()
                    41 -> ARouter.getInstance().build(RouterTag.BasicGrammarActivity).navigation()
                    42 -> ARouter.getInstance().build(RouterTag.NDKPractiseActivity).navigation()
                    43 -> ARouter.getInstance().build(RouterTag.TestTabLayoutActivity).navigation()
                    44 -> ARouter.getInstance().build(RouterTag.TestCoordinatorTabLayout).navigation()
                    45 -> ARouter.getInstance().build(RouterTag.TestsAvatarsOverlapActivity).navigation()
                    46 -> ARouter.getInstance().build(RouterTag.TestCalendarEventManagerActivity).navigation()
                    47 -> ARouter.getInstance().build(RouterTag.TestShadowBgActivity).navigation()
                }
            }
        })

        //8.RecyclerView 自动滑动到底部
//        rv_main.scrollToPosition(mainlistadapter.itemCount - 1)
        rv_main.scrollToPosition(mainlistadapter.itemCount - 1)
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
     *
     * priority 数值越大，优先级越高,默认优先级为0
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    fun EventBusTestMessage(messageevent: MessageEvent) {
        if (EventTag.event_test == messageevent.message_type) {
            LogUtils.i("收到测试消息 =-= ${messageevent.message}")
        }
    }

    /**
     * TaskRemovedService
     */
    var serviceIntent: Intent? = null

    //启动服务
    private fun startTaskRemovedService() {
        serviceIntent = Intent(this@MainActivity, TaskRemovedService::class.java)
        startService(serviceIntent)
    }

    private fun stopTaskRemovedService() {
        if (!StringUtils.isBlank(serviceIntent)) {
            stopService(serviceIntent)
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
        "协程Coroutine测试",
        "lambda方法使用和高级函数的使用",
        "SVG动画测试,自定义View---Canvas的使用",
        "设计模式---命令模式",
        "Rxjava2使用方法",
        "反射的使用方法",
        "录屏测试",
        "贝塞尔曲线",
        "属性动画和视图动画",
        "自定义验证码输入框样式",
        "自定义日历控件",
        "图片选择工具 PictureSelector",
        "滑动单项选择框 LoopView",
        "kotlin-基本语法 BasicGrammarActivity",
        "NDK 测试练习",
        "TabLayout测试练习",
        "CoordinatorTabLayout测试练习",
        "测试头像重叠/时间选择器",
        "日历事件管理器，增加一个事件提醒功能啦",
        "测试背景阴影效果"
    )

    /**
     * 从测试数据集合中取一定数量的数据
     */
    //标记号
    var tempnum = 0

    //每次取多少
    var pernum = 10

    //测试数据中剩余的数
    var items_remaining = items.size

    //测试数据临时数据，随时变化
    var tempList = mutableListOf<String>()

    private fun takeDataFromItems() {
        if (tempnum == 0 || tempnum > 0 && items_remaining > pernum) {//第一次取 或者 items_remaining大于pernum
            for (i in tempnum until tempnum + pernum) {
                tempList.add(items[i])
            }
            tempnum += pernum
            items_remaining = (items_remaining - pernum)
        } else if (items_remaining == 0) {//剩余为0
            return
        } else {//items_remaining小于pernum
            for (i in tempnum until items.size) {
                tempList.add(items[i])
                tempnum = i
            }
            tempnum = items.size
            items_remaining = 0
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }

        stopTaskRemovedService();
    }
}
