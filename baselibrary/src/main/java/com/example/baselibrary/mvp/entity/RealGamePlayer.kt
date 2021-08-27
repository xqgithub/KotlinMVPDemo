package com.example.baselibrary.mvp.entity

import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.mvp.view.IGamePlayer
import com.example.baselibrary.utils.LogUtils

/**
 * Date:2021/8/27
 * Time:16:06
 * author:joker
 * 委托-被委托对象，本场景中的游戏代练
 */
class RealGamePlayer(private val name: String) : IGamePlayer {

    var delegated_properties_a: String by Delegate()
    var delegated_properties_b: Int by Delegate()

    override fun rank() {
        LogUtils.i(
            ConfigConstants.TAG_ALL, "$name =-= 开始打排位了"
        )
    }

    override fun upgrade() {
        LogUtils.i(ConfigConstants.TAG_ALL, "$name =-= 排位比赛中晋级了")
    }
}