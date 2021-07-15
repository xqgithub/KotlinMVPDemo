package com.example.kotlinmvpdemo.mvp.presenters

import com.example.kotlinmvpdemo.mvp.views.TestLoopViewView
import com.weigan.loopview.LoopView
import javax.inject.Inject

class TestLoopViewPresenter @Inject constructor(val view: TestLoopViewView) {


    /**
     * 初始化LoopView
     */
    fun initLoopView(loopView: LoopView) {
        var testDatas = arrayOf("很优秀哦～", "作业完成的非常棒哦，请继续保持！", "完成的非常认真，给你点个赞！", "写的很赞，为你的努力鼓掌", "全班最棒", "你太优秀了", "我是海贼王路飞大人，哈哈；你是海贼王的助手，索隆，哟嚯嚯！")
        val list: ArrayList<String> = ArrayList()
        for (arg in testDatas) {
            list.add(arg)
        }

        //设置原始数据
        loopView.setItems(list)
        //设置初始位置
        loopView.setInitPosition(1)
    }


}
