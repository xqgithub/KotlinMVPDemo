package com.example.baselibrary.designpatterns.simplefactory

import com.example.baselibrary.utils.LogUtils

/**
 * 简单工厂模式---具体产品类
 */
class ConcreteProductSimple {

    /**
     * 具体产品类A
     */
    class ProductA : ProductSimple() {
        override fun show() {
            LogUtils.i("这是产品A")
        }
    }

    /**
     * 具体产品类B
     */
    class ProductB : ProductSimple() {
        override fun show() {
            LogUtils.i("这是产品B")
        }
    }

}