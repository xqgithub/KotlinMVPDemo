package com.example.baselibrary.designpatterns.intermediary

/**
 * 中介者模式---抽象同事类
 */
abstract class HousePerson(houseMediator: HouseMediator) {

    var houseMediator: HouseMediator? = null

    init {
        this.houseMediator = houseMediator
    }

    //发布消息
    abstract fun send(msg: String)

    //接受消息
    abstract fun getNotice(msg: String)
}