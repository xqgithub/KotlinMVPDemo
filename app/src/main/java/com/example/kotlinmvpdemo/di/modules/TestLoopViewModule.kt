package com.example.kotlinmvpdemo.di.modules


import com.example.kotlinmvpdemo.di.scopes.TestLoopViewActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.TestLoopViewPresenter
import com.example.kotlinmvpdemo.mvp.views.TestLoopViewView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class TestLoopViewModule @Inject constructor(private val view: TestLoopViewView) {


    @TestLoopViewActivityScope
    @Provides
    fun provideView(): TestLoopViewView {
        return view
    }


    @TestLoopViewActivityScope
    @Provides
    fun providegetPresenter(
        view: TestLoopViewView
    ): TestLoopViewPresenter {
        return TestLoopViewPresenter(view)
    }
}
