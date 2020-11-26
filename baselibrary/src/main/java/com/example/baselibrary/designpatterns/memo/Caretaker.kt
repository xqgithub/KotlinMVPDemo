package com.example.baselibrary.designpatterns.memo

/**
 * 备忘录模式---负责人角色，备忘录管理类
 */
class Caretaker {

    var mMemento: Memento? = null

    fun setMemento(memento: Memento) {
        this.mMemento = memento
    }

    fun getMemento(): Memento {
        return mMemento!!
    }
}