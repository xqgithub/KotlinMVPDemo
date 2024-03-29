package com.example.baselibrary.constants

object RouterTag {

    //testlibary---testMainActivity
    const val TestMainActivity = "/testlibary/TestMainActivity"

    //testlibary---CoroutineActivity
    const val CoroutineActivity = "/testlibary/CoroutineActivity"

    //testlibary---LambdaActivity
    const val LambdaActivity = "/testlibary/LambdaActivity"

    //testlibary---TestSVGActivity
    const val TestSVGActivity = "/testlibary/TestSVGActivity"

    //testlibary---Rxjava2UseActivity
    const val Rxjava2UseActivity = "/testlibary/Rxjava2UseActivity"

    //testlibary---ReflectionActivity
    const val ReflectionActivity = "/testlibary/ReflectionActivity"

    //testlibary---TestScreenRecordActivity
    const val TestScreenRecordActivity = "/testlibary/TestScreenRecordActivity"

    //testlibary---TestBezierActivity
    const val TestBezierActivity = "/testlibary/TestBezierActivity"

    //testlibary---TestPropertyAnimationActivity
    const val TestPropertyAnimationActivity = "/testlibary/TestPropertyAnimationActivity"

    //testlibary---TestCustomInputBoxActivity
    const val TestCustomInputBoxActivity = "/testlibary/TestCustomInputBoxActivity"

    //testlibary---TestCalendarViewActivity
    const val TestCalendarViewActivity = "/testlibary/TestCalendarViewActivity"

    //testlibary---TestPictureSelectorActivity
    const val TestPictureSelectorActivity = "/testlibary/TestPictureSelectorActivity"

    //testlibary---TestTabLayoutActivity
    const val TestTabLayoutActivity = "/testlibary/TestTabLayoutActivity"

    //testlibary---TestCoordinatorTabLayout
    const val TestCoordinatorTabLayout = "/testlibary/TestCoordinatorTabLayout"

    //testlibary---TestsAvatarsOverlapActivity
    const val TestsAvatarsOverlapActivity = "/testlibary/TestsAvatarsOverlapActivity"

    //testlibary---TestCalendarEventManagerActivity
    const val TestCalendarEventManagerActivity = "/testlibary/TestCalendarEventManagerActivity"

    //testlibary---TestShadowBgActivity
    const val TestShadowBgActivity = "/testlibary/TestShadowBgActivity"

    //testlibary---TestShadowBgActivity
    const val TestLoadSirActivity = "/testlibary/TestLoadSirActivity"

    //testlibary---TestServiceStartupModeActivity
    const val TestServiceStartupModeActivity = "/testlibary/TestServiceStartupModeActivity"

    //app---MainActivity
    const val MainActivity = "/app/MainActivity"

    //app---TestProductFlavorsActivity
    const val TestProductFlavorsActivity = "/app/TestProductFlavorsActivity"

    //app---TestLoopViewActivity
    const val TestLoopViewActivity = "/app/TestLoopViewActivity"

    //app---TestPictureSelectorMainActivity
    const val TestPictureSelectorMainActivity = "/app/TestPictureSelectorMainActivity"

    //app---BasicGrammarActivity
    const val BasicGrammarActivity = "/app/BasicGrammarActivity"

    //app---NDKPractiseActivity
    const val NDKPractiseActivity = "/app/NDKPractiseActivity"


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