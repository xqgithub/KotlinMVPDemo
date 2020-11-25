package com.example.baselibrary.designpatterns.iterator

/**
 *迭代器模式---具体迭代器实现
 */
class ConcreteIteratorMe(aggregate: Aggregate) : IteratorMe {

    var maggregate: Aggregate? = null

    init {
        this.maggregate = aggregate
    }


    //当前索引
    var index: Int = 0

    override fun hasnext(): Boolean {
        return index < maggregate!!.size()
    }


    override fun next(): Any {
        return maggregate!!.getlocation(index++)
    }
}