package com.example.kotlinmvpdemo.di.modules

import com.example.kotlinmvpdemo.di.scopes.OneFragmentScope
import com.example.kotlinmvpdemo.mvp.presenters.OneFragmentPresenter
import com.example.kotlinmvpdemo.mvp.views.FragmentOneView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class OneFragmentModule @Inject constructor(private val fragmentOneView: FragmentOneView) {

    @OneFragmentScope
    @Provides
    fun provideView(): FragmentOneView {
        return fragmentOneView
    }

    @OneFragmentScope
    @Provides
    fun providegetPresenter(fragmentOneView: FragmentOneView): OneFragmentPresenter {
        return OneFragmentPresenter(fragmentOneView)
    }
}