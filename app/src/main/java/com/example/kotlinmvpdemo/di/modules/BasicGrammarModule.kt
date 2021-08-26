package com.example.kotlinmvpdemo.di.modules


import com.example.kotlinmvpdemo.di.scopes.BasicGrammarActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.BasicGrammarPresenter
import com.example.kotlinmvpdemo.mvp.views.BasicGrammarView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class BasicGrammarModule @Inject constructor(private val view: BasicGrammarView) {


    @BasicGrammarActivityScope
    @Provides
    fun provideView(): BasicGrammarView {
        return view
    }


    @BasicGrammarActivityScope
    @Provides
    fun providegetPresenter(
        view: BasicGrammarView
    ): BasicGrammarPresenter {
        return BasicGrammarPresenter(view)
    }
}
