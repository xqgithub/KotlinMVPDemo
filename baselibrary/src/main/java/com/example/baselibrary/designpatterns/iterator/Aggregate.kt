package com.example.baselibrary.designpatterns.iterator

/**
 * 迭代器模式---容器基本功能，提供创建迭代器接口
 */
interface Aggregate {

    //容器大小
    fun size(): Int

    //得到获取中指定位置的号码
    fun getlocation(location: Int): String

    //添加号码到容器
    fun add(tel: String)

    //从容器中删除号码
    fun remove(tel: String)

    //返回迭代器对象
    fun getIterator(): IteratorMe

}