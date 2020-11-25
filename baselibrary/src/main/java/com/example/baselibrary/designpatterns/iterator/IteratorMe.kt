package com.example.baselibrary.designpatterns.iterator

/**
 * 迭代器模式---抽象迭代接口
 */
interface IteratorMe {

    //是否存在下一条记录
    fun hasnext(): Boolean

    //返回当前记录并且移动到下一条
    fun next(): Any


}