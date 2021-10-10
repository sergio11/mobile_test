package sanchez.sanchez.sergio.androidmobiletest.di.component

import dagger.Subcomponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.core.ActivityComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.splash.SplashFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.modules.core.ActivityModule
import sanchez.sanchez.sergio.androidmobiletest.di.scopes.PerActivity
import sanchez.sanchez.sergio.androidmobiletest.ui.features.splash.SplashScreenActivity

/**
 * Splash Activity Component
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface SplashActivityComponent: ActivityComponent {

    fun inject(activity: SplashScreenActivity)

    fun splashFragmentComponent(): SplashFragmentComponent
}