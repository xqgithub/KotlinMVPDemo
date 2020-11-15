package com.example.baselibrary.designpatterns.singleton

/**
 * 单例模式---静态内部类
 */
class StaticInnerClassKotlin private constructor() {

    companion object {
        val instance = StaticInnerClassKotlinHolder.holder
    }

    private object StaticInnerClassKotlinHolder {
        val holder = StaticInnerClassKotlin()
    }
}