package com.example.baselibrary.designpatterns.chainresponsibility

/**
 * 责任链模式---抽象处理者
 */
abstract class Postman {

    //下一个快递员
    var nextPostman: Postman? = null

    /**
     * 派送快递
     */
    abstract fun handleCourier(address: String)


}