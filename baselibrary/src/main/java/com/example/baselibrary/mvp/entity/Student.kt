package com.example.baselibrary.mvp.entity

import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils

/**
 * Date:2021/8/26
 * Time:15:46
 * author:joker
 * 测试 属性和字段
 */
class Student(val name: String) {

    //init加载模块在次构造函数constructor之前
    init {
        LogUtils.i(ConfigConstants.TAG_ALL, "我的是第一个加载的 =-= ${name.toUpperCase()}")
    }

    /**
     * 如果类有一个主构造函数，每个次构造函数需要委托给主构造函数， 可以直接委托或者通过别的次构造函数间接委托。
     * 委托到同一个类的另一个构造函数用 this 关键字即可
     */
    constructor(name: String, age: Int) : this(name) {
        LogUtils.i(
            ConfigConstants.TAG_ALL, "我的名字是 =-= $name",
            "我的年龄是 =-= $age"
        )
    }

    var lastName: String = "zhouxingxing"
        get() = field.toUpperCase()   // 将变量赋值后转换为大写

    var num: Int = 100
        get() = field      // 后端变量
        set(value) {
            if (value < 10) {// 如果传入的值小于 10 返回该值
                field = value
            } else {// 如果传入的值大于等于 10 返回 -1
                field = -1
            }
        }

    


}