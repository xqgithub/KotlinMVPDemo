package com.example.baselibrary.designpatterns.abstractfactory

/**
 * 抽象工厂模式---具体工厂类
 */
class ConcreteFactoryAbstract {

    /**
     * 生产联想电脑（Intel,SamsungMemory,SeagateHD）
     */
    class lianxiang : FactoryAbstract() {
        override fun createCPU(): ProductAbstract.Cpu {
            return ConcreteProductAbstract.IntelCPU()
        }

        override fun createMemory(): ProductAbstract.Memory {
            return ConcreteProductAbstract.SamsungMemory()
        }

        override fun createHD(): ProductAbstract.HD {
            return ConcreteProductAbstract.SeagateHD()
        }
    }

    /**
     * 生产惠普电脑（AMD,KingstonMemory,WdHD）
     */
    class HP : FactoryAbstract() {
        override fun createCPU(): ProductAbstract.Cpu {
            return ConcreteProductAbstract.AMDCPU()
        }

        override fun createMemory(): ProductAbstract.Memory {
            return ConcreteProductAbstract.KingstonMemory()
        }

        override fun createHD(): ProductAbstract.HD {
            return ConcreteProductAbstract.WdHD()
        }
    }


}