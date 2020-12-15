package com.example.baselibrary.designpatterns.intermediary

/**
 * 中介者模式---抽象中介者
 */
interface HouseMediator {

    //通知方法
    fun notice(housePerson: HousePerson, msg: String)

}