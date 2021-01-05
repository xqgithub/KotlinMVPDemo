package com.example.baselibrary.designpatterns.command

import com.example.baselibrary.utils.LogUtils

/**
 * 命令模式-调用者角色
 */
class Invoker(command: Command) {
    private var command: Command = command

    fun action() {
        LogUtils.i("调用者执行命令")
        command.execute()
    }

}