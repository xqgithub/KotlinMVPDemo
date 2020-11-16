package com.example.baselibrary.designpatterns.abstractfactory

import com.example.baselibrary.utils.LogUtils

/**
 * 抽象工厂模式---具体产品类
 */
class ConcreteProductAbstract {

    /**
     * 具体产品类---IntelCPU
     */
    class IntelCPU : ProductAbstract.Cpu() {
        override fun showCpu() {
            LogUtils.i("这是Intel CPU")
        }
    }

    /**
     * 具体产品类---AMDCPU
     */
    class AMDCPU : ProductAbstract.Cpu() {
        override fun showCpu() {
            LogUtils.i("这是AMD CPU")
        }
    }

    /**
     * 具体产品类---SamsungMemory
     */
    class SamsungMemory : ProductAbstract.Memory() {
        override fun showMemory() {
            LogUtils.i("这是Samsung Memory")
        }
    }

    /**
     * 具体产品类---KingstonMemory
     */
    class KingstonMemory : ProductAbstract.Memory() {
        override fun showMemory() {
            LogUtils.i("这是Kingston Memory")
        }
    }

    /**
     * 具体产品类---SeagateHD
     */
    class SeagateHD : ProductAbstract.HD() {
        override fun showHD() {
            LogUtils.i("这是Seagate HD")
        }
    }

    /**
     * 具体产品类---WdHD
     */
    class WdHD : ProductAbstract.HD() {
        override fun showHD() {
            LogUtils.i("这是Wd HD")
        }
    }
}