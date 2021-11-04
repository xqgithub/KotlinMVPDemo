package ${escapeKotlinIdentifiers(packageName)}.ui.activities

import ${superClassFqcn}
import android.os.Bundle
import ${escapeKotlinIdentifiers(packageName)}.R
import ${escapeKotlinIdentifiers(packageName)}.base.BaseActivity
import ${escapeKotlinIdentifiers(packageName)}.common.ConfigConstants
import ${escapeKotlinIdentifiers(packageName)}.ui.presenters.${subordinate}Presenter
import ${escapeKotlinIdentifiers(packageName)}.ui.views.${subordinate}View
import ${escapeKotlinIdentifiers(packageName)}.utils.LogUtils



class ${activityClass} : BaseActivity(),${subordinate}View{

private val mPresenter: ${subordinate}Presenter by lazy { ${subordinate}Presenter(this, this) }

    override fun onBeforeSetContentLayout() {
    }

        override fun getLayoutId():Int{
            return R.layout.${layoutName}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initUiView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun showLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "开始加载")
    }

    override fun hideLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "停止加载")
    }

}
