package com.example.baselibrary.mvp.entity

import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils

/**
 * Date:2021/8/26
 * Time:16:12
 * author:joker
 * 要被集成的父类
 */
open class Parents {//要求父类必须有open标注，不然无法被子类所继承

    open val base_a: Int = 1
    open val base_b: Int = 2

    /**
     * Base类的加载块
     * 设计一个基类时，应该避免在构造函数、属性初始化器以及 init 块中使用 open 成员
     */
    init {
        LogUtils.i(ConfigConstants.TAG_ALL, "初始化父类，Parents")
    }

    /**
     *被覆盖的函数必须有open标注，不然无法被重写
     */
    open fun allowRewrite() {
        LogUtils.i(ConfigConstants.TAG_ALL, "我是父类，允许被重写的方法allowRewrite")
    }

    fun unAllowRewrite() {
        LogUtils.i(ConfigConstants.TAG_ALL, "我是父类，不允许被重写的方法unAllowRewrite")
    }
}