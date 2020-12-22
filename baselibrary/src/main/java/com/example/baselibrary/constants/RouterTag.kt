package com.example.baselibrary.constants

object RouterTag {
    //testlibary---testMainActivity
    const val TestMainActivity = "/testlibary/TestMainActivity"

    //app---TestProductFlavorsActivity
    const val TestProductFlavorsActivity = "/app/TestProductFlavorsActivity"

    //app---MainActivity
    const val MainActivity = "/app/MainActivity"


    //全局降解
    const val DegradeService = "/service/degradeservice"

    //用withObject 传值的时候 需要先写一个 JsonServiceImpl类 继承 SerializationService
    const val SerializationService = "/service/json"

    //跳转前预处理
    const val PretreatmentService = "/service/pretreatmentservice"


    /** ARouter页面标识，告诉拦截器 该页面准备做什么**/
    //需要做登录处理
    const val login = 100
}