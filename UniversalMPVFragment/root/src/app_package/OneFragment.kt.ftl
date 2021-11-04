package ${escapeKotlinIdentifiers(packageName)}.ui.fragments

import android.os.Bundle
import ${escapeKotlinIdentifiers(packageName)}.R
import ${escapeKotlinIdentifiers(packageName)}.base.BaseFragment
import ${escapeKotlinIdentifiers(packageName)}.common.ConfigConstants
import ${escapeKotlinIdentifiers(packageName)}.ui.presenters.OneFragmentPresenter
import ${escapeKotlinIdentifiers(packageName)}.ui.views.OneFragmentView
import ${escapeKotlinIdentifiers(packageName)}.utils.LogUtils

class ${fragmentClass} : BaseFragment(), ${fragmentClass}View {

    val mPresenter: ${fragmentClass}Presenter by lazy { ${fragmentClass}Presenter(mActivity, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.${fragmentName}
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * Fragment 延迟加载数据
     */
    override fun lazyLoad() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
