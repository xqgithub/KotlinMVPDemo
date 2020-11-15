package com.example.baselibrary.designpatterns.singleton

/**
 * 单例模式---懒汉式(线程安全)
 */
class LazyManKotlin2 private constructor() {

    companion object {
        private var lazymankotlin: LazyManKotlin2? = null
            get() {
                if (field == null) {
                    field = LazyManKotlin2()
                }
                return field
            }

        @Synchronized
        fun getInstance(): LazyManKotlin2 {
            return lazymankotlin!!
        }
    }
}