package com.example.baselibrary.designpatterns.observer

/**
 *观察者模式---具体被观察者
 */
class ConcreteObservable : Observabledesign {

    //保存收件人（观察者）的信息
    private val mutableList = mutableListOf<Observerdesign>()

    override fun add(observer: Observerdesign) {//添加收件人
        mutableList.add(observer)
    }

    override fun remove(observer: Observerdesign) {//移除收件人
        mutableList.remove(observer)
    }

    override fun notify(message: String) {//逐一通知收件人（观察者）
        for (arg in mutableList) {
            arg.update(message)
        }
    }
}