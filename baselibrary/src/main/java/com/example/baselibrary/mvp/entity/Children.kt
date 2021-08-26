package com.example.baselibrary.mvp.entity

import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils

/**
 * Date:2021/8/26
 * Time:16:19
 * author:joker
 * 1.继承的子类
 * 2.属性覆盖
 * 3.内部类
 */
class Children(name: String, lastName: String) : Parents() {


    /**
     * Inherited的加载块
     *
     */
    init {
        LogUtils.i(ConfigConstants.TAG_ALL, "初始化子类，Children")
    }


    /**
     * 1.属性覆盖与方法覆盖类似；在超类中声明然后在派生类中重新声明的属性必须以 override 开头，并且它们必须具有兼容的类型。
     * 2.每个声明的属性可以由具有初始化器的属性或者具有 getter 方法的属性覆盖。
     */
    override val base_a: Int
        get() = 7

    /**
     * 1.你也可以用一个 var 属性覆盖一个 val 属性，但反之则不行。
     * 2.这是允许的，因为一个 val 属性本质上声明了一个 getter 方法，而将其覆盖为 var 只是在子类中额外声明一个 setter 方法。
     */
    override var base_b: Int = 0
        get() = field
        set(value) {
            if (value > 1) {
                field = 77
            } else {
                field = -1
            }
        }

    /**
     * 1.函数上必须加上override标注,才有能重写
     * 2.子类不允许和父类有重名的函数名，如果父类没有加open
     * 3.super<X>来消除歧义,表示是调用的是父类方法
     * 4.标记为override 的成员本身是开放的，也就是说，它可以在子类中覆盖。如果你想禁止再次覆盖，使用 final 关键字
     */
    override fun allowRewrite() {
        super.allowRewrite()
        LogUtils.i(ConfigConstants.TAG_ALL, "我是子类,允许被重写了的方法allowRewrite")
    }

    /**
     * 内部类
     * 类可以标记为 inner
     * 在一个内部类中访问外部类的超类，可以通过由外部类名限定的 super 关键字来实现：super@Outer：
     */
    inner class SecondChild {
        private val a: Int = 177

        fun testa() {
            super@Children.allowRewrite()// 调用 Parents 实现的 allowRewrite()
        }

        fun testb() {
            LogUtils.i(ConfigConstants.TAG_ALL, "这是一个内部类的方法 =-= $a")
        }
    }
}