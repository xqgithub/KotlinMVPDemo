package com.example.baselibrary.designpatterns.visitor

import com.example.baselibrary.utils.LogUtils

/**
 * 访问者模式---具体访问者
 */
class ConcreteVisitor {

    /**
     * 闲人
     */
    class Idler(name: String) : Visitor {
        var name: String? = null

        init {
            this.name = name
        }

        override fun visit(music: ConcreteWebVideoStie.Music) {
            LogUtils.i("$name 浏览音乐网站 ${music.name}")
            music.playMusic()
        }

        override fun visit(video: ConcreteWebVideoStie.Video) {
            LogUtils.i("$name 浏览视频网站 ${video.name}")
            video.playVideo()
        }
    }

    /**
     * 忙人
     */
    class Busy(name: String) : Visitor {
        var name: String? = null

        init {
            this.name = name
        }


        override fun visit(music: ConcreteWebVideoStie.Music) {
            LogUtils.i("$name 浏览音乐网站 ${music.name}")
            music.download()
        }

        override fun visit(video: ConcreteWebVideoStie.Video) {
            LogUtils.i("$name 浏览视频网站 ${video.name}")
            video.download()
        }
    }
}