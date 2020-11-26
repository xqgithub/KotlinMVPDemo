package com.example.baselibrary.designpatterns.memo

import com.example.baselibrary.utils.LogUtils

/**
 * 备忘录模式---发起人角色,游戏类，提供存档和读档的功能
 */
class Originator {

    //等级
    var level = 1
    //金币
    var coin = 0

    /**
     * 玩游戏
     */
    fun play() {
        LogUtils.i("升级了")
        level++
        LogUtils.i("当前等级为：${level}")
        LogUtils.i("获得金币为：32")
        coin += 32
        LogUtils.i("当前金币为：${coin}")
    }

    /**
     * 退出游戏
     */
    fun exit() {
        LogUtils.i("退出游戏,退出游戏时的属性：等级-${level},金币-${coin}")
    }

    /**
     * 创建备忘录类，即存档
     */
    fun createMemento(): Memento {
        val memento = Memento(level, coin)
        return memento
    }

    /**
     * 读档
     */
    fun getMemento(memento: Memento) {
        level = memento.level
        coin = memento.coin
        LogUtils.i("游戏人物状态：等级-${level},金币-${coin}")
    }
}