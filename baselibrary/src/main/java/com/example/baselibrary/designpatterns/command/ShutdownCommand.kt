package com.example.baselibrary.designpatterns.command

import com.example.baselibrary.utils.LogUtils

/**
 * 命令模式-具体命令角色
 */
class ShutdownCommand(receiver: Receiver) : Command {//关机命令

    //接受
    private var receiver: Receiver = receiver

    override fun execute() {
        LogUtils.i("命令角色执行关机命令")
        receiver.action()//调用接受者
    }

}