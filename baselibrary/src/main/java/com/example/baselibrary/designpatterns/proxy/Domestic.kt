package com.example.baselibrary.designpatterns.proxy

import com.blankj.utilcode.util.LogUtils

/**
 * Date:2023/4/24
 * Time:15:01
 * author:joker
 * 真实主题类
 */
class Domestic : People {

    override fun buy() {//具体实现
        LogUtils.i("国内要买一个包")
    }
}