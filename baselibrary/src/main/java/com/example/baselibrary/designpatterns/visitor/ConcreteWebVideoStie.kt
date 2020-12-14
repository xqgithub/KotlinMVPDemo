package com.example.baselibrary.designpatterns.visitor

import com.example.baselibrary.utils.LogUtils

/**
 * 访问者模式---具体元素
 */
class ConcreteWebVideoStie {

    /**
     * 音乐类
     */
    class Music(name: String) : WebVideoSite(name) {
        override fun accept(visitor: Visitor) {
            visitor.visit(this)
        }

        override fun download() {
            LogUtils.i("下载音乐")
        }

        /**
         * 音乐独有的方法
         */
        fun playMusic() {
            LogUtils.i("播放音乐ing")
        }
    }

    /**
     * 视频类
     */
    class Video(name: String) : WebVideoSite(name) {

        override fun accept(visitor: Visitor) {
            visitor.visit(this)
        }

        override fun download() {
            LogUtils.i("下载视频")
        }

        /**
         * 视频类独有的方法
         */
        fun playVideo() {
            LogUtils.i("播放视频ing")
        }
    }
}