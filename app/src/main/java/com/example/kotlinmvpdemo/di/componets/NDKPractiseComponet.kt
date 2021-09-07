package com.example.kotlinmvpdemo.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.NDKPractiseModule
import com.example.kotlinmvpdemo.di.scopes.NDKPractiseActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.NDKPractisePresenter
import com.example.kotlinmvpdemo.mvp.ui.activities.NDKPractiseActivity
import dagger.Component

@NDKPractiseActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [NDKPractiseModule::class])
interface NDKPractiseComponet {
    fun inject(activity: NDKPractiseActivity)
    fun getPresenter(): NDKPractisePresenter
}