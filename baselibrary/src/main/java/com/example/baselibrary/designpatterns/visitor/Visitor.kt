package com.example.baselibrary.designpatterns.visitor

/**
 * 访问者模式---抽象访问者
 */
interface Visitor {

    //访问音乐类
    fun visit(music: ConcreteWebVideoStie.Music)

    //访问视频类
    fun visit(video: ConcreteWebVideoStie.Video)
}