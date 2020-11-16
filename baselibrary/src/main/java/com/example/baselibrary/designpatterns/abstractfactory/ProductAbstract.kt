package com.example.baselibrary.designpatterns.abstractfactory

/**
 * 抽象工厂模式---抽象产品类
 */
abstract class ProductAbstract {

    /**
     * 抽象产品类---CPU
     */
    abstract class Cpu {
        abstract fun showCpu()
    }

    /**
     * 抽象产品类---内存
     */
    abstract class Memory {
        abstract fun showMemory()
    }

    /**
     * 抽象产品类---硬盘
     */
    abstract class HD {
        abstract fun showHD()
    }
}