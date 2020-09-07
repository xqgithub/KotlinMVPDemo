package com.example.baselibrary.di.componets

import android.content.Context
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.data.api.ApiService
import com.example.baselibrary.di.modules.ApiServiceModule
import com.example.baselibrary.di.modules.MyAppModule
import dagger.Component
import javax.inject.Singleton

/**
 * 全局注解器
 */
@Singleton
@Component(modules = [MyAppModule::class, ApiServiceModule::class])
interface MyAppComponet {
    fun getContext(): Context
    fun inject(myapp: MyApplication)
    fun getApiService(): ApiService
}