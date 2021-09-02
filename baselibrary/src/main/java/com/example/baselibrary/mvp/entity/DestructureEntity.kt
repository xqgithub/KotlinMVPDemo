package com.example.baselibrary.mvp.entity

/**
 * Date:2021/9/2
 * Time:11:19
 * author:joker
 * 解构 实体类
 */
data class DestructureEntity(var name: String, var age: Int, var addr: String) {
    var mobile: String? = null
    operator fun component4(): String {
        return this.mobile!!
    }
}