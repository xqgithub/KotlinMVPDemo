package ${escapeKotlinIdentifiers(packageName)}.di.modules


import ${escapeKotlinIdentifiers(packageName)}.di.scopes.${fragmentClass}Scope
import ${escapeKotlinIdentifiers(packageName)}.mvp.presenters.${fragmentClass}Presenter
import ${escapeKotlinIdentifiers(packageName)}.mvp.views.${fragmentClass}View
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ${fragmentClass}Module @Inject constructor(private val view: ${fragmentClass}View){


    @${fragmentClass}Scope
    @Provides
    fun provideView(): ${fragmentClass}View {
        return view
    }


    @${fragmentClass}Scope
    @Provides
    fun providegetPresenter(
        view: ${fragmentClass}View
    ): ${fragmentClass}Presenter {
        return ${fragmentClass}Presenter(view)
    }
}
