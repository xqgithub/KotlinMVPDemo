package com.example.kotlinmvpdemo.mvp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.EventTag
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.customizeviews.ScaleInTransformer
import com.example.baselibrary.mvp.entity.MessageEvent
import com.example.baselibrary.mvp.entity.Person
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerProductFlavorsComponet
import com.example.kotlinmvpdemo.di.modules.ProductFlavorsModule
import com.example.kotlinmvpdemo.mvp.ui.adapter.ViewPager2Adapter
import com.example.kotlinmvpdemo.mvp.ui.fragments.OneFragment
import com.example.kotlinmvpdemo.mvp.ui.fragments.TwoFragment
import com.example.kotlinmvpdemo.mvp.views.ProductFlavorsView
import kotlinx.android.synthetic.main.activity_productflavors.*
import org.greenrobot.eventbus.EventBus

/**
 * 测试多版本差异化
 * @param extras 标识，告诉拦截器 该页面准备做什么
 */
@Route(path = RouterTag.TestProductFlavorsActivity, extras = RouterTag.login)
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

    /** Fragment **/
    //Fragment管理器
    lateinit var fragmentManager: FragmentManager

    //Fragment的事务
    lateinit var transaction: FragmentTransaction

    //Fragment 1号页面
    lateinit var onefragment: OneFragment

    //Fragment 2号页面
    lateinit var twofragment: TwoFragment

    lateinit var fragments: MutableList<Fragment>

    /** Fragment **/

    /** ViewPager2 **/
    lateinit var viewpager2adapter: ViewPager2Adapter


    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerProductFlavorsComponet.builder()
            .myAppComponet(myAppComponet)
            .productFlavorsModule(ProductFlavorsModule(this))
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
        return R.layout.activity_productflavors
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    /**
     * 初始化数据
     */
    @SuppressLint("NewApi")
    private fun initData() {
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


        //获得版本的package_id
        val package_id = PublicPracticalMethodFromJAVA.getInstance()
            .getMetaDataValue(this@TestProductFlavorsActivity, ConfigConstants.PACKAGE_ID)
        if (package_id == ConfigConstants.ceshi) {
            tv_name.text = getString(R.string.productflavors_ceshi)
        } else if (package_id == ConfigConstants.shengchan) {
            tv_name.text = getString(R.string.productflavors_shengchan)
        }


        tv_eventbus.setOnClickListener {
            /**发送eventbus测试消息**/
            var messageevent: MessageEvent = MessageEvent()
            messageevent.message_type = EventTag.event_test
            messageevent.message = "测试eventbus消息"
            EventBus.getDefault().post(messageevent)
            finish()
        }


        /**Fragment**/
        //初始化FragmentManager
        fragmentManager = supportFragmentManager
        //开始事务
        transaction = fragmentManager.beginTransaction()
        //Fragment初始化
        onefragment = OneFragment()
        twofragment = TwoFragment()

        //添加Fragment, Activity 页面显示Fragment页面
//        transaction.add(R.id.fl_fragment_container, onefragment)
//        transaction.commit()
//        transaction.show(onefragment)

        /**ViewPager**/
        //添加Fragment的集合
        fragments = mutableListOf<Fragment>()
        fragments.add(onefragment)
        fragments.add(twofragment)

//        val MyViewPagerAdapter = MyViewPagerAdapter(fragmentManager, fragments)
//        vp_fragment_container.adapter = MyViewPagerAdapter
//        vp_fragment_container.addOnPageChangeListener(onPageChangeListener)
//        /** 当需要显示除0外的其他页码的时候 需要延迟加载，因为这个时候Fragment 还没有初始化 **/
//        Handler(Looper.getMainLooper()).postDelayed({
//            vp_fragment_container.currentItem = 0
//        }, 20)

        /** viewPager2 **/
//        var colors = arrayOf("#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC")
//        viewpager2adapter = ViewPager2Adapter(this@TestProductFlavorsActivity, colors)
//        vp2_fragment_container.adapter = viewpager2adapter
//        //是否静止滑动禁止滑动
//        vp2_fragment_container.isUserInputEnabled = false
//        //设置ViewPager2的滑动方向
//        vp2_fragment_container.orientation = ViewPager2.ORIENTATION_VERTICAL

        /** viewPager2 Fragment **/
        val adapterfragmentpager = AdapterFragmentPager(this@TestProductFlavorsActivity, fragments)
        vp2_fragment_container.adapter = adapterfragmentpager
        //预加载多少页
        vp2_fragment_container.offscreenPageLimit = 2
        //ViewPager2的滑动方向
        vp2_fragment_container.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //ViewPager2是否允许滑动
        vp2_fragment_container.isUserInputEnabled = true
        //组合的PageTransformer
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(MyApplication.myapplication.resources.getDimension(R.dimen.dimen_50x).toInt()))
        vp2_fragment_container.setPageTransformer(compositePageTransformer)
        //ViewPager2的滑动监听
        vp2_fragment_container.registerOnPageChangeCallback(onPageChangeCallback)
    }

    /**
     * 配合ViewPager的Fragment的适配器
     * 1.behavior参数传入FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,那么就调用setMaxLifecycle()方法将上一个Fragment的状态设置为STARTED，将当前要显示的Fragment的状态设置为RESUMED
     * 2.mBehavior的值为BEHAVIOR_SET_USER_VISIBLE_HINT，那么依然使用setUserVisibleHint()方法设置Fragment的可见性，相应地可以根据getUserVisibleHint()方法获取到Fragment是否可见，从而实现懒加载
     */
    @Suppress("DEPRECATION")
    @SuppressLint("WrongConstant")
    class MyViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        lateinit var fragments: MutableList<Fragment>

        constructor(manager: FragmentManager, fragments: MutableList<Fragment>) : this(manager) {
            this.fragments = fragments
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }


    }

    /**
     * ViewPager 监听
     */
    private var onPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        /**
         *  当currentItem 初始化为0 的时候  是不会走该方法的，其余的页码是会走的
         */
        override fun onPageSelected(position: Int) {
            when (position) {
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "被选择的是页面号码：${position}")
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }


    /**
     * Viewpager2  Fragment 适配器
     * 本身就时候懒加载的
     */
    class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        lateinit var fragments: MutableList<Fragment>

        constructor(fragmentActivity: FragmentActivity, fragments: MutableList<Fragment>) : this(fragmentActivity) {
            this.fragments = fragments
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    /**
     * ViewPager2的滑动监听
     */
    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "被选择的是页面号码：${position}")
        }
    }

    override fun onResume() {
        super.onResume()
    }

}