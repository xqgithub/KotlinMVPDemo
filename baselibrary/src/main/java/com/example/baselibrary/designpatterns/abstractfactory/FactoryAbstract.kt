package com.example.baselibrary.designpatterns.abstractfactory

/**
 * 抽象工厂模式---抽象工厂类
 */
abstract class FactoryAbstract {
    abstract fun createCPU(): ProductAbstract.Cpu
    abstract fun createMemory(): ProductAbstract.Memory
    abstract fun createHD(): ProductAbstract.HD
}