package com.example.baselibrary.designpatterns.simplefactory


/**
 * 简单工厂模式---工厂类
 */
class FactorySimple {
    companion object {
        /**
         * 正常简单工厂模式创建
         * 多了一个产品需要修改该类
         */
        fun create(productName: String): ProductSimple? {
            var product: ProductSimple? = null
            when (productName) {
                "A" -> product = ConcreteProductSimple.ProductA()
                "B" -> product = ConcreteProductSimple.ProductB()
            }
            return product
        }

        /**
         * 简单工厂模式优化，反射来创建
         * 不需要修改该类,但是生成对象时间比较长
         */
        fun <T : ProductSimple?> create(clz: Class<T>): T? {
            var product: ProductSimple? = null
            try {
                product = Class.forName(clz.name).newInstance() as ProductSimple //反射出实例
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return product as T?
        }
    }


}