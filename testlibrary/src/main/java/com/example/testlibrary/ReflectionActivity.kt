package com.example.testlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.clickWithTrigger
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_reflection.*

/**
 * 反射测试类
 * 1.什么是反射？
 * 反射就是在运行时才知道要操作的类是什么，并且可以在运行时获取类的完整构造，并调用对应的方法
 * 2.通过反射可以获取
 * (1)该类的所有的构造、属性、方法
 * (2)通过指定构造、属性、方法操作该类
 * 3.访问或操作类的私有变量和方法
 * (1)访问私有方法
 * (2)修改私有变量
 * (3)修改私有常量
 *
 */
@Route(path = RouterTag.ReflectionActivity)
class ReflectionActivity : BaseActivity() {
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
        return R.layout.activity_reflection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_reflect.clickWithTrigger(500) {
            val branch = et_reflect.text.toString().toInt()
            when (branch) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                1 -> testreflection()
                else -> showShortToastSafe("序号错误，请检查")
            }
        }
    }

    private fun testreflection() {
        //1.正向调用
        val person = Person()
        person.name = "lufei"
        LogUtils.i(ConfigConstants.TAG_ALL, "正向调用---" + person.getName1())

        //2.反射调用
        //获取Person的class属性
        val clazz = Person::class.java
        //获取所有的属性
        val fields = clazz.declaredFields
        fields.forEach {
            LogUtils.i(ConfigConstants.TAG_ALL, "反射调用---fields:$it")
        }
        //获取所有的方法
        val methods = clazz.declaredMethods
        methods.forEach {
            LogUtils.i(ConfigConstants.TAG_ALL, "反射调用---methods:$it")
        }
        //获取所有的构造
        val constructors = clazz.declaredConstructors
        constructors.forEach {
            LogUtils.i(ConfigConstants.TAG_ALL, "反射调用---constructors:$it")
        }
        //获取构造
        val constructor = clazz.getConstructor().newInstance()
        //获取指定属性
        clazz.getDeclaredField("name").apply {
            isAccessible = true
        }.set(constructor, "索隆")
        val name = clazz.getDeclaredField("name").apply {
            isAccessible = true
        }.get(constructor)

        clazz.getDeclaredField("age").apply {
            isAccessible = true
        }.set(constructor, 77)
        val age = clazz.getDeclaredField("age").apply {
            isAccessible = true
        }.get(constructor)

        clazz.getDeclaredField("address").apply {
            isAccessible = true
        }.set(constructor, 22222222)
        val adder = clazz.getDeclaredField("address").apply {
            isAccessible = true
        }.get(constructor)


        //获取指定方法
        val getName1 = clazz.getDeclaredMethod("getName1").invoke(constructor)
        val getAge1 = clazz.getDeclaredMethod("getAge1").apply {
            isAccessible = true
        }.invoke(constructor)


        LogUtils.i(ConfigConstants.TAG_ALL, "反射调用---$getName1 : $getAge1 : $adder")
    }


    class Person {
        var name: String = ""
        var age: Int = 0
        private val address: Int = 1

        fun getName1(): String {
            return "我的名字是：$name"
        }

        private fun getAge1(): Int {
            return age
        }

    }
}