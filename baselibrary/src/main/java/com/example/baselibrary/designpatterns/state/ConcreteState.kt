package com.example.baselibrary.designpatterns.state

import com.example.baselibrary.utils.LogUtils

/**
 * 状态模式---具体状态
 */
class ConcreteState {

    class DogState : PersonState {
        override fun movies() {
            LogUtils.i("单身狗只配看岛国的电影")
        }

        override fun shopping() {
            LogUtils.i("单身狗不配逛街")
        }
    }

    class LoveState : PersonState {
        override fun movies() {
            LogUtils.i("恋爱的人一起去电影院看电影")
        }

        override fun shopping() {
            LogUtils.i("恋爱的人一起去街上逛街")
        }
    }
}