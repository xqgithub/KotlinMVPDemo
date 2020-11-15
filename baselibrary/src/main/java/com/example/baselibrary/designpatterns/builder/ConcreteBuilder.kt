package com.example.baselibrary.designpatterns.builder

/**
 * 建造者模式---具体的建造者
 */
class ConcreteBuilder : Builder() {
    //创建产品实例
    val computer: Computer = Computer()

    override fun buildCPU(cpu: String) {
        computer.mCPU = cpu
    }

    override fun buildMemory(memory: String) {
        computer.mMemory = memory
    }

    override fun buildHD(hd: String) {
        computer.mHD = hd
    }

    override fun getCreateComputer(): Computer {
        return computer
    }
}