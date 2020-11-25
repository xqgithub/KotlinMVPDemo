package com.example.baselibrary.designpatterns.template

import com.example.baselibrary.utils.LogUtils

/**
 * 模板方法模式---抽象方法类
 */
abstract class PostmanTemplate {//抽象快递员类

    /**
     * 派送流程
     */
    fun post() {
        prepare()
        call()
        if (isSign()) sign() else refuse()
    }

    /**
     * 固定流程，父类实现
     */
    fun prepare() {
        LogUtils.i("快递已达到，准备派送")
    }

    /**
     * 联系收货人
     */
    abstract fun call()

    /**
     * 是否已经签收
     * 钩子方法
     */
    open fun isSign(): Boolean {
        return true
    }

    /**
     * 签收快递
     */
    fun sign() {
        LogUtils.i("客户已签收，上报系统")
    }

    /**
     * 拒绝签收
     * 钩子方法
     */
    open fun refuse() {

    }

}