package sanchez.sanchez.sergio.androidmobiletest.di.component.splash

import dagger.Subcomponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.core.FragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.modules.splash.SplashUIModule
import sanchez.sanchez.sergio.androidmobiletest.di.scopes.PerFragment
import sanchez.sanchez.sergio.androidmobiletest.ui.features.splash.SplashFragment

@PerFragment
@Subcomponent(modules = [ SplashUIModule::class])
interface SplashFragmentComponent: FragmentComponent {

    /**
     * Inject into Splash Fragment
     */
    fun inject(splashFragment: SplashFragment)
}

