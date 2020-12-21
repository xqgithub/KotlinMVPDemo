package ${escapeKotlinIdentifiers(packageName)}.mvp.presenters

import ${escapeKotlinIdentifiers(packageName)}.mvp.views.${subordinate}View
import javax.inject.Inject

class ${subordinate}Presenter @Inject constructor(val view: ${subordinate}View){
}
