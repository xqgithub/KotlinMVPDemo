package com.example.baselibrary.designpatterns.observer

import com.example.baselibrary.utils.LogUtils

/**
 * 观察者模式---具体观察者
 */
class ConcreteObserver {

    /**
     * 男孩
     */
    class Boy(name: String) : Observerdesign {

        var mname: String? = null

        init {
            this.mname = name
        }

        override fun update(message: String) {//男孩的具体反应
            LogUtils.i("男孩${mname},收到了信息:${message}屁颠颠的去取快递")
        }
    }

    /**
     * 女孩
     */
    class Girl(name: String) : Observerdesign {

        var mname: String? = null

        init {
            this.mname = name
        }

        override fun update(message: String) {//女孩的具体反应
            LogUtils.i("女孩${mname},收到了信息:${message}让男孩屁颠屁颠去取快递")
        }
    }

}