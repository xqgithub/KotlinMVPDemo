package com.example.baselibrary.designpatterns.iterator

/**
 * 迭代器模式---容器具体实现
 */
class ConcreteAggregate : Aggregate {

    //内部使用list来存储数据
    val mutableList = mutableListOf<String>()

    override fun size(): Int {
        return mutableList.size
    }

    override fun getlocation(location: Int): String {
        return mutableList[location]
    }

    override fun add(tel: String) {
        mutableList.add(tel)
    }

    override fun remove(tel: String) {
        mutableList.remove(tel)
    }

    override fun getIterator(): IteratorMe {
        return ConcreteIteratorMe(this)
    }
}