package com.example.baselibrary.designpatterns.factory

import com.example.baselibrary.utils.LogUtils

/**
 * 工厂方法模式---具体产品类
 */
class ConcreteProduct {

    /**
     * 具体产品类A
     */
    class ProductA : Product() {
        override fun show() {
            LogUtils.i("这是产品A")
        }
    }

    /**
     * 具体产品类B
     */
    class ProductB : Product() {
        override fun show() {
            LogUtils.i("这是产品B")
        }
    }

}