package com.example.baselibrary.designpatterns.strategy

/**
 * 策略模式---环境类
 */
class Environment(chasestragety: ChaseStragety) {

    var chasestragety: ChaseStragety? = null

    init {
        this.chasestragety = chasestragety
    }

    fun chase() {
        chasestragety!!.chase()
    }


}