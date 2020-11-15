package com.example.baselibrary.designpatterns.builder

/**
 * 建造者模式---指挥者
 */
class Director(builder: Builder) {
    private var mbuilder = builder

    constructor(
        builder: Builder,
        cpu: String,
        memory: String,
        hd: String
    ) : this(builder) {
        builder.buildCPU(cpu)
        builder.buildMemory(memory)
        builder.buildHD(hd)
    }

    fun Constructor(
        cpu: String,
        memory: String,
        hd: String
    ) {
        mbuilder!!.buildCPU(cpu)
        mbuilder!!.buildMemory(memory)
        mbuilder!!.buildHD(hd)
    }


}