package com.example.baselibrary.designpatterns.strategy

import com.example.baselibrary.utils.LogUtils

/**
 * 策略模式---具体策略类
 */
class ConcreteStragety {

    class ConcreteStragetyA : ChaseStragety() {
        override fun chase() {
            LogUtils.i("约妹子一起逛街")
        }
    }

    class ConcreteStragetyB : ChaseStragety() {
        override fun chase() {
            LogUtils.i("约妹子一起看电影")
        }
    }

    class ConcreteStragetyC : ChaseStragety() {
        override fun chase() {
            LogUtils.i("约妹子一起吃饭")
        }
    }


}