package com.example.baselibrary.designpatterns.singleton

/**
 * 单例模式---懒汉式(线程不安全)
 */
class LazyManKotlin private constructor() {

    companion object {
        private var lazymankotlin: LazyManKotlin? = null
            get() {
                if (field == null) {
                    field = LazyManKotlin()
                }
                return field
            }

        fun getInstance(): LazyManKotlin {
            return lazymankotlin!!
        }
    }
}