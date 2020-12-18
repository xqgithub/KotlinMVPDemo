package com.example.kotlinmvpdemo.di.componets

import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.OneFragmentModule
import com.example.kotlinmvpdemo.di.scopes.OneFragmentScope
import com.example.kotlinmvpdemo.mvp.presenters.OneFragmentPresenter
import com.example.kotlinmvpdemo.mvp.ui.fragments.OneFragment
import dagger.Component

@OneFragmentScope
@Component(dependencies = [MyAppComponet::class], modules = [OneFragmentModule::class])
interface OneFragmentComponet {
    fun inject(fragment: OneFragment)
    fun getPresenter(): OneFragmentPresenter
}