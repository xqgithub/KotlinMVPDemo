package com.example.baselibrary.designpatterns.observer

/**
 * 观察者模式---抽象被观察者
 */
interface Observabledesign {

    fun add(observer: Observerdesign) //添加观察者

    fun remove(observer: Observerdesign) //删除观察者

    fun notify(message: String) //通知观察者
}