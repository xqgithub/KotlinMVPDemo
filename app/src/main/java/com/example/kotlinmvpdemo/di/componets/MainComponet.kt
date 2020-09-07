package com.example.kotlinmvpdemo.di.componets

import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.MainModule
import com.example.kotlinmvpdemo.di.scopes.MainActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.MainPresenter
import com.example.kotlinmvpdemo.mvp.ui.activities.MainActivity
import dagger.Component

@MainActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [MainModule::class])
interface MainComponet {
    fun inject(activity: MainActivity)
    fun getPresenter(): MainPresenter
}