package ${escapeKotlinIdentifiers(packageName)}.ui.presenters

import android.content.Context
import ${escapeKotlinIdentifiers(packageName)}.base.BasePresenter
import ${escapeKotlinIdentifiers(packageName)}.ui.views.OneFragmentView
import ${escapeKotlinIdentifiers(packageName)}.ui.views.StartUpView


class ${fragmentClass}Presenter(private var mContext: Context, private var mView: ${fragmentClass}View) : BasePresenter<${fragmentClass}View>(mContext, mView) {

}
