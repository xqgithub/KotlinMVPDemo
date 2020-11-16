package com.example.baselibrary.designpatterns.factory

/**
 * 工厂方法模式---具体工厂类
 */
class ConcreteFactory {

    /**
     * 具体工厂类A
     */
    class FactoryA : Factory() {
        override fun create(): Product {
            return ConcreteProduct.ProductA()
        }
    }

    /**
     * 具体工厂类B
     */
    class FactoryB : Factory() {
        override fun create(): Product {
            return ConcreteProduct.ProductB()
        }
    }
}