package com.example.baselibrary.designpatterns.interpreter

/**
 *解释器模式---创建环境主要包含解释器之外的全部信息，这里用来保存变量以及其值
 */
class Surroundings {

    //使用Map来保存结果
    val mutablemap = mutableMapOf<String, Any>()

    fun put(key: String, value: Any) {
        mutablemap[key] = value
    }


    fun get(key: String): Int {
        return mutablemap[key] as Int
    }
}