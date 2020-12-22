package ${escapeKotlinIdentifiers(packageName)}.di.componets


import com.example.baselibrary.di.componets.MyAppComponet
import ${escapeKotlinIdentifiers(packageName)}.di.modules.${fragmentClass}Module
import ${escapeKotlinIdentifiers(packageName)}.di.scopes.${fragmentClass}Scope
import ${escapeKotlinIdentifiers(packageName)}.mvp.presenters.${fragmentClass}Presenter
import ${escapeKotlinIdentifiers(packageName)}.mvp.ui.fragments.${fragmentClass}
import dagger.Component

@${fragmentClass}Scope
@Component(dependencies = [MyAppComponet::class], modules = [${fragmentClass}Module::class])
interface ${fragmentClass}Componet {
    fun inject(fragment: ${fragmentClass})
    fun getPresenter(): ${fragmentClass}Presenter
}