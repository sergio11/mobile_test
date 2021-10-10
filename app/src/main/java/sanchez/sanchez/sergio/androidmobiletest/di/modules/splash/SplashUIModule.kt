package sanchez.sanchez.sergio.androidmobiletest.di.modules.splash

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import sanchez.sanchez.sergio.androidmobiletest.di.modules.core.ViewModelModule
import sanchez.sanchez.sergio.androidmobiletest.di.modules.core.viewmodel.ViewModelKey
import sanchez.sanchez.sergio.androidmobiletest.di.scopes.PerFragment
import sanchez.sanchez.sergio.androidmobiletest.ui.features.splash.SplashViewModel

/**
 * Splash UI Module
 */
@Module(includes = [ ViewModelModule::class ])
abstract class SplashUIModule {

    @PerFragment
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(pokemonSplashViewModel: SplashViewModel): ViewModel
}