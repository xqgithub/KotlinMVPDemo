package com.example.kotlinmvpdemo.di.modules


import com.example.kotlinmvpdemo.di.scopes.NDKPractiseActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.NDKPractisePresenter
import com.example.kotlinmvpdemo.mvp.views.NDKPractiseView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class NDKPractiseModule @Inject constructor(private val view: NDKPractiseView) {


    @NDKPractiseActivityScope
    @Provides
    fun provideView(): NDKPractiseView {
        return view
    }


    @NDKPractiseActivityScope
    @Provides
    fun providegetPresenter(
        view: NDKPractiseView
    ): NDKPractisePresenter {
        return NDKPractisePresenter(view)
    }
}
