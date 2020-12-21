package ${escapeKotlinIdentifiers(packageName)}.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import ${escapeKotlinIdentifiers(packageName)}.di.modules.${subordinate}Module
import ${escapeKotlinIdentifiers(packageName)}.di.scopes.${activityClass}Scope
import ${escapeKotlinIdentifiers(packageName)}.mvp.presenters.${subordinate}Presenter
import ${escapeKotlinIdentifiers(packageName)}.mvp.ui.activities.${activityClass}
import dagger.Component

@${activityClass}Scope
@Component(dependencies = [MyAppComponet::class], modules = [${subordinate}Module::class])
interface ${subordinate}Componet {
    fun inject(activity: ${activityClass})
    fun getPresenter(): ${subordinate}Presenter
}