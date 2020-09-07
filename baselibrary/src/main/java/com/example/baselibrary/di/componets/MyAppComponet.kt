package com.example.baselibrary.di.componets

import android.content.Context
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.data.api.ApiService
import com.example.baselibrary.data.database.MyDaoMaster
import com.example.baselibrary.di.modules.ApiServiceModule
import com.example.baselibrary.di.modules.DBHelperModule
import com.example.baselibrary.di.modules.MyAppModule
import com.example.kotlinmvpdemo.DaoMaster
import com.example.kotlinmvpdemo.DaoSession
import dagger.Component
import javax.inject.Singleton


/**
 * 全局注解器
 */
@Singleton
@Component(
    modules = [MyAppModule::class,
        ApiServiceModule::class,
        DBHelperModule::class]
)
interface MyAppComponet {
    fun getContext(): Context
    fun inject(myapp: MyApplication)
    fun getApiService(): ApiService
    fun getDBHelperModule(): DBHelperModule
    fun getDevOpenHelper(): MyDaoMaster
    fun getDaoMaster(): DaoMaster
    fun getDaoSession(): DaoSession
}