package com.example.kotlinmvpdemo.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.TestLoopViewModule
import com.example.kotlinmvpdemo.di.scopes.TestLoopViewActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.TestLoopViewPresenter
import com.example.kotlinmvpdemo.mvp.ui.activities.TestLoopViewActivity
import dagger.Component

@TestLoopViewActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [TestLoopViewModule::class])
interface TestLoopViewComponet {
    fun inject(activity: TestLoopViewActivity)
    fun getPresenter(): TestLoopViewPresenter
}