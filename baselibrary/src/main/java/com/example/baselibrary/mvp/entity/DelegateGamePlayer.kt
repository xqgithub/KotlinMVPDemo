package com.example.baselibrary.mvp.entity

import com.example.baselibrary.mvp.view.IGamePlayer

/**
 * Date:2021/8/27
 * Time:16:08
 * author:joker
 * 委托-委托对象
 */
class DelegateGamePlayer(private val player: IGamePlayer) : IGamePlayer by player {
}