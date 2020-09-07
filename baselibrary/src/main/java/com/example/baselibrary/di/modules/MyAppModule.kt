package com.example.baselibrary.di.modules

import android.content.Context
import com.example.baselibrary.application.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  MyApp 的 Module
 */
@Module
class MyAppModule @Inject constructor(private val myApp: MyApplication) {

    var myapplication: MyApplication = myApp

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return myApp
    }
}