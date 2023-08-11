package com.example.testlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.ReflectionUtils
import com.example.baselibrary.utils.StringUtils
import com.example.baselibrary.utils.clickWithTrigger
import com.example.baselibrary.utils.inputCheckLimit
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

    private var branch = -1


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
        et_reflect.inputCheckLimit("[^0-9]") {
            if (!StringUtils.isBlank(it)) {
                branch = it.toInt()
            }
        }
        tv_reflect.clickWithTrigger(500) {
            when (branch) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                1 -> testreflection()
                2 -> testreflection2()
                3 -> testreflection3()
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


    /**
     * 调用反射工具类，无参构造
     */
    private fun testreflection2() {
        val classname = Person::class.java.name
        //1.初始化类
        val aclass = ReflectionUtils.getInstance().getMyClass(classname)
        //2.初始化空构造
        val constructor = aclass.getConstructor().newInstance()
        //设置属性
        ReflectionUtils.getInstance().setField(aclass, constructor, "name", "娜美", true)
        //获取属性的值
        val a = ReflectionUtils.getInstance().getField(aclass, constructor, "name", true)
        LogUtils.i(ConfigConstants.TAG_ALL, "name:$a")
        //执行方法
        val result = ReflectionUtils.getInstance().runMethod(aclass, constructor, "getName1", true)
        LogUtils.i(ConfigConstants.TAG_ALL, "name:$result")
    }

    /**
     * 调用反射工具类，有参构造
     */
    private fun testreflection3() {
        val classname = Person::class.java.name
        //1.初始化类
        val aclass = ReflectionUtils.getInstance().getMyClass(classname)
        //2.初始化构造
        val p3 = arrayOf<Class<*>>(String::class.java, Int::class.java)
        val constructor = aclass.getConstructor(*p3).newInstance("乌索普", 30)
        //获取属性的值
        val a = ReflectionUtils.getInstance().getField(aclass, constructor, "name", true)
        LogUtils.i(ConfigConstants.TAG_ALL, "name:$a")
        //执行方法
        val result = ReflectionUtils.getInstance().runMethod(aclass, constructor, "getAge1", true)
        LogUtils.i(ConfigConstants.TAG_ALL, "age:$result")
    }


    /**
     * 父类 人
     */
    open class Person {
        var name: String = "1111"
        var age: Int = 0
        private val address: Int = 1

        constructor() {

        }

        constructor(name: String, age: Int) {
            this.name = name
            this.age = age
        }


        fun getName1(): String {
            return "我的名字是：$name"
        }

        private fun getAge1(): Int {
            return age
        }
    }

    /**
     * 继承类  男人
     */
    class Man : Person() {
        val gender = "male"

    }


}