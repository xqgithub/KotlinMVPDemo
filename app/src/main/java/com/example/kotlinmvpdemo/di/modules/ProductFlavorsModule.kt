package com.example.kotlinmvpdemo.di.modules

import com.example.kotlinmvpdemo.di.scopes.MainActivityScope
import com.example.kotlinmvpdemo.mvp.presenters.ProductFlavorsPresenter
import com.example.kotlinmvpdemo.mvp.views.ProductFlavorsView
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * 版本差异化的Module
 */
@Module
class ProductFlavorsModule @Inject constructor(
    private val productFlavorsView: ProductFlavorsView
) {

    @MainActivityScope
    @Provides
    fun provideView(): ProductFlavorsView {
        return productFlavorsView
    }

    @MainActivityScope
    @Provides
    fun providegetPresenter(
        productFlavorsView: ProductFlavorsView
    ): ProductFlavorsPresenter {
        return ProductFlavorsPresenter(productFlavorsView)
    }
}