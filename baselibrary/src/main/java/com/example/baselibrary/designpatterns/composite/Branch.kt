package com.example.baselibrary.designpatterns.composite

import com.blankj.utilcode.util.LogUtils

/**
 * Date:2023/4/24
 * Time:15:51
 * author:joker
 * 树枝节点
 * 树枝节点能够删除添加叶子或树枝
 */
class Branch(name: String) : PageElement(name) {

    override fun addPageElement(pageElement: PageElement) {
        mPageElements.add(pageElement)
    }

    override fun rmPageElement(pageElement: PageElement) {
        mPageElements.remove(pageElement)
    }

    override fun clear() {
        mPageElements.clear()
    }

    override fun print(placeholder: String) {
        //利用递归来打印文件夹结构
        println("$placeholder └── $mName")
        if (mPageElements.size > 0) {
            mPageElements.forEach {
                it.print("$placeholder  ")
            }
        }
    }
}