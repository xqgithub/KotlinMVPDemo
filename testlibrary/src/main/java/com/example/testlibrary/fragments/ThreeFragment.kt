package com.example.testlibrary.fragments

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baselibrary.base.BaseFragment
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.testlibrary.R
import com.example.testlibrary.adapter.TestListAdapter
import kotlinx.android.synthetic.main.fragment_three.*

class ThreeFragment(var mTitlte: String) : BaseFragment() {


    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_three
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        isLoadData = true
    }


    override fun lazyLoad() {
        initRecyclerview()
    }

    private fun initRecyclerview() {
        val linearlayoutmanager = LinearLayoutManager(requireActivity())
        linearlayoutmanager.orientation = RecyclerView.VERTICAL
        rv_threefragment.layoutManager = linearlayoutmanager

        val adapter = TestListAdapter(requireActivity(), items)
        rv_threefragment.adapter = adapter
        adapter.notifyDataSetChanged()
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
        "CoordinatorTabLayout测试练习"
    )


}
