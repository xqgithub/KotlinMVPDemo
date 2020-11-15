package com.example.baselibrary.designpatterns.builder

/**
 * 建造者模式---抽象建造者
 */
abstract class Builder {
    //组装CPU
    abstract fun buildCPU(cpu: String)

    //组装内存
    abstract fun buildMemory(memory: String)

    //组装硬盘
    abstract fun buildHD(hd: String)

    //返回组装好的电脑
    abstract fun getCreateComputer(): Computer
}