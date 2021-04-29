package com.example.baselibrary.mvp.entity

/**
 * Date:2021/4/29
 * Time:15:05
 * author:joker
 */
class Point(x: Float, y: Float) {
    //设置两个变量用于记录坐标的位置
    var x: Float? = null
    var y: Float? = null

    init {
        this.x = x
        this.y = y
    }
}