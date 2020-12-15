package com.example.baselibrary.designpatterns.intermediary

import com.example.baselibrary.utils.LogUtils

/**
 * 中介者模式---具体同事类
 */
class ConcreteHousePerson {

    /**
     * 买房者类
     */
    class Purchaser(houseMediator: HouseMediator) : HousePerson(houseMediator) {

        var mHouseMediator: HouseMediator? = null

        init {
            this.mHouseMediator = houseMediator
        }

        override fun send(msg: String) {
            LogUtils.i("买房者发布买房消息：${msg}")
            mHouseMediator!!.notice(this, msg)
        }

        override fun getNotice(msg: String) {
            LogUtils.i("买房者收到买房消息：${msg}")
        }
    }

    /**
     * 卖房子类
     */
    class Landlord(houseMediator: HouseMediator) : HousePerson(houseMediator) {

        var mHouseMediator: HouseMediator? = null

        init {
            this.mHouseMediator = houseMediator
        }

        override fun send(msg: String) {
            LogUtils.i("卖房者发布卖房消息：${msg}")
            mHouseMediator!!.notice(this, msg)
        }

        override fun getNotice(msg: String) {
            LogUtils.i("卖房者收到卖房消息：${msg}")
        }
    }
}