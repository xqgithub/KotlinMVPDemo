package com.example.kotlinmvpdemo.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import com.example.kotlinmvpdemo.di.modules.TestPictureSelectorMainModule
import com.example.kotlinmvpdemo.di.scopes.TestPictureSelectorMainActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.TestPictureSelectorMainPresenter
import com.example.kotlinmvpdemo.mvp.ui.activities.TestPictureSelectorMainActivity
import dagger.Component

@TestPictureSelectorMainActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [TestPictureSelectorMainModule::class])
interface TestPictureSelectorMainComponet {
    fun inject(activity: TestPictureSelectorMainActivity)
    fun getPresenter(): TestPictureSelectorMainPresenter
}