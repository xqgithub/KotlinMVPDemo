package com.example.baselibrary.mvp.entity

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Date:2021/8/27
 * Time:15:53
 * author:joker
 * 委托属性类
 * 1.委托属性不需要实现任何的接口，但是要提供getValue()方法(如果是var的话要提供setValue()方法)，方法前加operator关键字。
 */
class Delegate<T : Any> : ReadWriteProperty<Any?, T> {

    var delegate_value: String = ""

    private var value: T? = null

    /**
     * thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型
     * property —— 必须是类型 KProperty<*>或其超类型。
     * value —— 必须与属性同类型或者是它的子类型。
     */
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
    }


    //    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
////        L.i("$thisRef, '${property.name}',$delegate_value")
//        return delegate_value
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        delegate_value = value
////        L.i("$value has been assigned to '${property.name} in $thisRef.'")
//    }
}