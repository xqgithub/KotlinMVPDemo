package ${escapeKotlinIdentifiers(packageName)}.ui.presenters

import android.content.Context
import ${escapeKotlinIdentifiers(packageName)}.base.BasePresenter
import ${escapeKotlinIdentifiers(packageName)}.ui.views.${subordinate}View

class ${subordinate}Presenter constructor(private var mContext: Context,private var mView: ${subordinate}View):BasePresenter<${subordinate}View>(mContext, mView){
}
