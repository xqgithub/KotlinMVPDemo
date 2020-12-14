package com.example.baselibrary.designpatterns.visitor

/**
 * 访问者模式---抽象元素
 */
abstract class WebVideoSite(name: String) {

    var name: String? = null

    init {
        this.name = name
    }

    //定义一个抽象的受访问方法
    abstract fun accept(visitor: Visitor)

    //下载资源
    abstract fun download();
}