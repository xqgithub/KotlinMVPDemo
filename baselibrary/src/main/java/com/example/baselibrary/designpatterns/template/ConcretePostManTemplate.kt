package com.example.baselibrary.designpatterns.template

import com.example.baselibrary.utils.LogUtils

/**
 * 模板方法模式---具体实现类
 */
class ConcretePostManTemplate {

    /**
     * 同意签收
     */
    class PostManA : PostmanTemplate() {
        override fun call() {
            LogUtils.i("联系A先生并送到门口")
        }
    }

    /**
     * 拒绝签收
     */
    class PostManB : PostmanTemplate() {

        override fun call() {
            LogUtils.i("联系B先生并送到门口")
        }

        /**
         * 拒绝签收
         */
        override fun isSign(): Boolean {
            return false
        }

        /**
         * 拒绝签收后流程
         */
        override fun refuse() {
            LogUtils.i("商品不符合，拒绝签收")
        }
    }
}