package com.example.kotlinmvpdemo.di.modules


import com.example.kotlinmvpdemo.di.scopes.TwoFragmentScope
import com.example.kotlinmvpdemo.mvp.presenters.TwoFragmentPresenter
import com.example.kotlinmvpdemo.mvp.views.TwoFragmentView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class TwoFragmentModule @Inject constructor(private val view: TwoFragmentView) {


    @TwoFragmentScope
    @Provides
    fun provideView(): TwoFragmentView {
        return view
    }


    @TwoFragmentScope
    @Provides
    fun providegetPresenter(
        view: TwoFragmentView
    ): TwoFragmentPresenter {
        return TwoFragmentPresenter(view)
    }
}
