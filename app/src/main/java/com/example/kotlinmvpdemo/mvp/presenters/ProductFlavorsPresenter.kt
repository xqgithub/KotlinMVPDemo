package com.example.kotlinmvpdemo.mvp.presenters

import com.example.kotlinmvpdemo.mvp.views.ProductFlavorsView
import javax.inject.Inject

/**
 * 版本差异化的Presenter
 */
class ProductFlavorsPresenter @Inject constructor(
    val productFlavorsView: ProductFlavorsView
) {


}