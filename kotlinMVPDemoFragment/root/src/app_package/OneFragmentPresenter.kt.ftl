package ${escapeKotlinIdentifiers(packageName)}.mvp.presenters

import ${escapeKotlinIdentifiers(packageName)}.mvp.views.${fragmentClass}View
import javax.inject.Inject

class ${fragmentClass}Presenter @Inject constructor(val view: ${fragmentClass}View){
}
