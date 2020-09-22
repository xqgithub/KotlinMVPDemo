package com.example.kotlinmvpdemo.di.componets

import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.ProductFlavorsModule
import com.example.kotlinmvpdemo.di.scopes.MainActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.ProductFlavorsPresenter
import com.example.kotlinmvpdemo.mvp.ui.activities.TestProductFlavorsActivity
import dagger.Component

@MainActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [ProductFlavorsModule::class])
interface ProductFlavorsComponet {
    fun inject(activity: TestProductFlavorsActivity)
    fun getPresenter(): ProductFlavorsPresenter
}