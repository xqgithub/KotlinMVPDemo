package ${packageName}.di.componets

import com.example.baselibrary.di.componets.MyAppComponet
import ${packageName}.di.modules.${activityClass}Module
import ${packageName}.di.scopes.${activityClass}ActivityScope
import ${packageName}.mvp.presenters.${activityClass}Presenter
import ${packageName}.mvp.ui.activities.${activityClass}Activity
import dagger.Component

@${activityClass}ActivityScope
@Component(dependencies = [MyAppComponet::class], modules = [${activityClass}Module::class])
interface ${activityClass}Componet {
    fun inject(activity: ${activityClass}Activity)
    fun getPresenter(): ${activityClass}Presenter
}