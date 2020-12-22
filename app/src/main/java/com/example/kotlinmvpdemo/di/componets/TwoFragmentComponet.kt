package com.example.kotlinmvpdemo.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.TwoFragmentModule
import com.example.kotlinmvpdemo.di.scopes.TwoFragmentScope
import com.example.kotlinmvpdemo.mvp.presenters.TwoFragmentPresenter
import com.example.kotlinmvpdemo.mvp.ui.fragments.TwoFragment
import dagger.Component

@TwoFragmentScope
@Component(dependencies = [MyAppComponet::class], modules = [TwoFragmentModule::class])
interface TwoFragmentComponet {
    fun inject(fragment: TwoFragment)
    fun getPresenter(): TwoFragmentPresenter
}