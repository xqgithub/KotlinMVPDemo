package com.example.baselibrary.designpatterns.intermediary

import com.example.baselibrary.utils.LogUtils

/**
 * 中介者模式---具体中介者类
 */
class ConcreteHouseMediator {

    /**
     * 链家中介卖房子
     */
    class Lianjia : HouseMediator {

        var mpurchaser: ConcreteHousePerson.Purchaser? = null
        var mlandlord: ConcreteHousePerson.Landlord? = null

        fun setPurchaser(purchaser: ConcreteHousePerson.Purchaser) {
            this.mpurchaser = purchaser
        }

        fun setLandlord(landlord: ConcreteHousePerson.Landlord) {
            this.mlandlord = landlord
        }

        override fun notice(housePerson: HousePerson, msg: String) {
            LogUtils.i("中介收到消息：${msg}，并转发给相应的人群")
            if (housePerson is ConcreteHousePerson.Purchaser) {//买房者发布的消息
                mlandlord!!.getNotice(msg)
            } else if (housePerson is ConcreteHousePerson.Landlord) {//卖房者发布的消息
                mpurchaser!!.getNotice(msg)
            }
        }
    }
}