package com.example.baselibrary.designpatterns.command

import com.example.baselibrary.utils.LogUtils

/**
 * 命令模式-接收者角色
 */
class Receiver {

    fun action() {//接收者执行具体的操作
        LogUtils.i("接收者执行具体的操作")
        LogUtils.i("开始执行关机操作：")
        LogUtils.i("退出所有程序进程")
        LogUtils.i("关机～")
    }
}