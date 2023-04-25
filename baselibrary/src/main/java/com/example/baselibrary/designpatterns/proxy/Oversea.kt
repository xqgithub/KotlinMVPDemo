package com.example.baselibrary.designpatterns.proxy

import com.blankj.utilcode.util.LogUtils

/**
 * Date:2023/4/24
 * Time:15:03
 * author:joker
 * 代理类
 */
class Oversea(var mPeople: People) : People {

    override fun buy() {
        LogUtils.i("我是海外代购")
        mPeople.buy()
    }
}