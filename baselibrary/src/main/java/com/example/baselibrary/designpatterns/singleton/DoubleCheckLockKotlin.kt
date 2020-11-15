package com.example.baselibrary.designpatterns.singleton

/**
 * 单例模式---双重检查锁定（DCL）
 */
class DoubleCheckLockKotlin private constructor() {

    companion object {
        val doublechecklockkotlin: DoubleCheckLockKotlin by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DoubleCheckLockKotlin()
        }
    }
}
