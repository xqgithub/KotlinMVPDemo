package com.example.baselibrary.designpatterns.composite

import com.blankj.utilcode.util.LogUtils

/**
 * Date:2023/4/24
 * Time:15:35
 * author:joker
 * 叶子节点
 * 由于没有分支，所以一些添加删除操作是实现不了的
 */
class Leaf(name: String) : PageElement(name) {

    override fun addPageElement(pageElement: PageElement) {
        throw UnsupportedOperationException("支持此操作")
    }

    override fun rmPageElement(pageElement: PageElement) {
        throw UnsupportedOperationException("支持此操作")
    }

    override fun clear() {
        throw UnsupportedOperationException("支持此操作")
    }

    override fun print(placeholder: String) {
        println("$placeholder ── $mName")
    }
}