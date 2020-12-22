package com.example.kotlinmvpdemo.di.modules

import com.example.kotlinmvpdemo.di.scopes.OneFragmentScope
import com.example.kotlinmvpdemo.mvp.presenters.OneFragmentPresenter
import com.example.kotlinmvpdemo.mvp.views.OneFragmentView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class OneFragmentModule @Inject constructor(private val oneFragmentView: OneFragmentView) {

    @OneFragmentScope
    @Provides
    fun provideView(): OneFragmentView {
        return oneFragmentView
    }

    @OneFragmentScope
    @Provides
    fun providegetPresenter(oneFragmentView: OneFragmentView): OneFragmentPresenter {
        return OneFragmentPresenter(oneFragmentView)
    }
}