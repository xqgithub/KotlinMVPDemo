package com.example.kotlinmvpdemo.di.modules


import com.example.kotlinmvpdemo.di.scopes.TestPictureSelectorMainActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.TestPictureSelectorMainPresenter
import com.example.kotlinmvpdemo.mvp.views.TestPictureSelectorMainView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class TestPictureSelectorMainModule @Inject constructor(private val view: TestPictureSelectorMainView) {


    @TestPictureSelectorMainActivityScope
    @Provides
    fun provideView(): TestPictureSelectorMainView {
        return view
    }


    @TestPictureSelectorMainActivityScope
    @Provides
    fun providegetPresenter(
        view: TestPictureSelectorMainView
    ): TestPictureSelectorMainPresenter {
        return TestPictureSelectorMainPresenter(view)
    }
}
