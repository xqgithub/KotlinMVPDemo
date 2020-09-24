package ${packageName}.di.modules

import ${packageName}.di.scopes.${activityClass}ActivityScope
import ${packageName}.mvp.presenters.${activityClass}Presenter
import ${packageName}.mvp.views.${activityClass}View
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ${activityClass}Module @Inject constructor(
    private val view: ${activityClass}View
) {

    @${activityClass}ActivityScope
    @Provides
    fun provideView(): ${activityClass}View {
        return view
    }

    @${activityClass}ActivityScope
    @Provides
    fun providegetPresenter(
        view: ${activityClass}View
    ): ${activityClass}Presenter {
        return ${activityClass}Presenter(view)
    }
}