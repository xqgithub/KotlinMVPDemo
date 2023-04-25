package com.example.baselibrary.designpatterns.composite

/**
 * Date:2023/4/24
 * Time:15:29
 * author:joker
 * 组合模式---抽象组件角色
 */
abstract class PageElement(var name: String) {

    //用来保存页面元素
    protected var mPageElements = mutableListOf<PageElement>()

    var mName: String = name

    //添加栏目或者具体内容
    abstract

    fun addPageElement(pageElement: PageElement)

    //删除栏目或者具体内容
    abstract fun rmPageElement(pageElement: PageElement)

    //清空所有
    abstract fun clear()

    //打印页面结构
    abstract fun print(placeholder: String)
}