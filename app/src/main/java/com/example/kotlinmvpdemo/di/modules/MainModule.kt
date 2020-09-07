package com.example.kotlinmvpdemo.di.modules

import com.example.baselibrary.data.api.ApiService
import com.example.kotlinmvpdemo.di.scopes.MainActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.MainPresenter
import com.example.kotlinmvpdemo.mvp.views.MainView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * 首页的Module
 */
@Module
class MainModule @Inject constructor(private val mainview: MainView) {

    @MainActivityScope
    @Provides
    fun provideView(): MainView {
        return mainview
    }

    @MainActivityScope
    @Provides
    fun providegetPresenter(mainview: MainView, apiService: ApiService): MainPresenter {
        return MainPresenter(mainview, apiService)
    }
}