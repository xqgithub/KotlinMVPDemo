package com.example.baselibrary.designpatterns.state

/**
 *状态模式---切换状态
 */
class ChangeState {

    var mpersonstate: PersonState? = null

    /**
     * 设置状态
     */
    private fun setPersonState(personstate: PersonState) {
        this.mpersonstate = personstate
    }

    /**
     * 切换热恋状态
     */
    fun fallInLove() {
        setPersonState(ConcreteState.LoveState())
    }

    /**
     * 切换单身狗状态
     */
    fun disappointmentInLove() {
        setPersonState(ConcreteState.DogState())
    }

    /**
     * 看电影
     */
    fun movies() {
        mpersonstate!!.movies()
    }

    /**
     * 逛街
     */
    fun shopping() {
        mpersonstate!!.shopping()
    }

}