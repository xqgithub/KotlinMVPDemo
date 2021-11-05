package com.example.testlibrary

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.testlibrary.fragments.ThreeFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_testcoordinatortablayout.*

/**
 * Date:2021/11/5
 * Time:15:26
 * author:dimple
 * 测试CoordinatorTabLayout
 */
@Route(path = RouterTag.TestCoordinatorTabLayout)
class TestCoordinatorTabLayout : BaseActivity() {


    private lateinit var fragmentManager: FragmentManager
    private lateinit var transaction: FragmentTransaction
    private lateinit var fragments: MutableList<Fragment>
    private var mTitles = arrayOf(
        "热门", "iOS", "Android", "前端", "后端", "设计", "工具资源"
    )


    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            R.color.full_red
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_testcoordinatortablayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    /**
     * 加载数据
     */
    private fun initData() {
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()

        /** 添加Fragment的集合 **/
        fragments = mutableListOf()
        for (i in mTitles.indices) {
            fragments.add(ThreeFragment(mTitles[i]))
        }


        /** 初始化 ViewPager**/
        val myViewPagerAdapter = TestTabLayoutActivity.MyViewPagerAdapter(fragmentManager, fragments, mTitles)
        vp_fragment_container.adapter = myViewPagerAdapter
        vp_fragment_container.addOnPageChangeListener(onPageChangeListener)

        /** 初始化 google TabLayout  **/
        tp_tabLayout.setupWithViewPager(vp_fragment_container)
        for (i in 0 until myViewPagerAdapter.count) {
            val tab: TabLayout.Tab? = tp_tabLayout.getTabAt(i)
            tab?.let {
                it.customView = getTabView(i)
            }
        }

        //去掉tab点击效果
        tp_tabLayout.tabRippleColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.transparent))
        //关联ViewPager
        tp_tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vp_fragment_container))
        tp_tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        vp_fragment_container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tp_tabLayout))

        tp_tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.let {
                    val tv_name = it.findViewById<TextView>(R.id.tv_name)
                    val iv_title = it.findViewById<ImageView>(R.id.iv_title)

                    tv_name.apply {
                        setTextColor(Color.parseColor("#ff4545"))
                        textSize = 18f
                    }
                    iv_title.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.let {
                    val tv_name = it.findViewById<TextView>(R.id.tv_name)
                    val iv_title = it.findViewById<ImageView>(R.id.iv_title)

                    tv_name.apply {
                        setTextColor(Color.parseColor("#000000"))
                        textSize = 14f
                    }
                    iv_title.visibility = View.GONE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        /** 当需要显示除0外的其他页码的时候 需要延迟加载，因为这个时候Fragment 还没有初始化 **/
        Handler(Looper.getMainLooper()).postDelayed({
            vp_fragment_container.currentItem = 1
            tp_tabLayout.getTabAt(1)!!.select()
        }, 20)


    }

    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val iv_title = view.findViewById<ImageView>(R.id.iv_title)
        tv_name.apply {
            text = mTitles[position]
            textSize = 14f
        }
        iv_title.setImageResource(R.drawable.af_nav_line_tablayout_new)
        return view
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
        lateinit var titleNames: Array<String>

        constructor(manager: FragmentManager, fragments: MutableList<Fragment>, titleNames: Array<String>) : this(manager) {
            this.fragments = fragments
            this.titleNames = titleNames
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleNames[position]
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
            userName.text = mTitles[position]
            LogUtils.i(ConfigConstants.TAG_ALL, "被选择的是页面号码：${position}")
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }


}