package com.example.kotlinmvpdemo.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.BasicGrammarModule
import com.example.kotlinmvpdemo.di.scopes.BasicGrammarActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.BasicGrammarPresenter
import com.example.kotlinmvpdemo.mvp.ui.activities.BasicGrammarActivity
import dagger.Component

@BasicGrammarActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [BasicGrammarModule::class])
interface BasicGrammarComponet {
    fun inject(activity: BasicGrammarActivity)
    fun getPresenter(): BasicGrammarPresenter
}