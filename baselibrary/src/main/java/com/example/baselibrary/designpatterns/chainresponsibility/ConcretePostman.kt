package com.example.baselibrary.designpatterns.chainresponsibility

import com.example.baselibrary.utils.LogUtils

/**
 * 责任链模式---具体处理者
 */
class ConcretePostman {

    /**
     * 北京快递员
     */
    class BeijingPostman : Postman() {
        override fun handleCourier(address: String) {
            if (address == "beijing") {
                LogUtils.i("派送到北京")
                return
            } else {//否则交给下一个快递员去处理
                nextPostman!!.handleCourier(address)
            }
        }
    }

    /**
     * 上海快递员
     */
    class ShangHaiPostman : Postman() {
        override fun handleCourier(address: String) {
            if (address == "shanghai") {
                LogUtils.i("派送到上海")
                return
            } else {//否则交给下一个快递员去处理
                nextPostman!!.handleCourier(address)
            }
        }
    }

    /**
     * 广州快递员
     */
    class GuangZhouPostman : Postman() {
        override fun handleCourier(address: String) {
            if (address == "guangzhou") {
                LogUtils.i("派送到广州")
                return
            } else {//否则交给下一个快递员去处理
                nextPostman!!.handleCourier(address)
            }
        }
    }
}