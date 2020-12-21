package ${escapeKotlinIdentifiers(packageName)}.di.modules


import ${escapeKotlinIdentifiers(packageName)}.di.scopes.${activityClass}Scope
import ${escapeKotlinIdentifiers(packageName)}.mvp.presenters.${subordinate}Presenter
import ${escapeKotlinIdentifiers(packageName)}.mvp.views.${subordinate}View
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ${subordinate}Module @Inject constructor(private val view: ${subordinate}View){


    @${activityClass}Scope
    @Provides
    fun provideView(): ${subordinate}View {
        return view
    }


    @${activityClass}Scope
    @Provides
    fun providegetPresenter(
        view: ${subordinate}View
    ): ${subordinate}Presenter {
        return ${subordinate}Presenter(view)
    }
}
