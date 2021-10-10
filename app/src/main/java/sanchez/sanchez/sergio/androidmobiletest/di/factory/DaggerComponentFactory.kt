package sanchez.sanchez.sergio.androidmobiletest.di.factory

import androidx.appcompat.app.AppCompatActivity
import sanchez.sanchez.sergio.androidmobiletest.AndroidMobileTestApp
import sanchez.sanchez.sergio.androidmobiletest.di.component.HomeActivityComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.ApplicationComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.DaggerApplicationComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.SplashActivityComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.character.CharacterDetailFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.character.CharacterListFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.splash.SplashFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.modules.core.ActivityModule
import sanchez.sanchez.sergio.androidmobiletest.di.modules.core.ApplicationModule

/**
 * Dagger Component Factory
 */
object DaggerComponentFactory {

    private var appComponent: ApplicationComponent? = null
    private var homeActivityComponent: HomeActivityComponent? = null
    private var splashActivityComponent: SplashActivityComponent? = null

    fun getAppComponent(app: AndroidMobileTestApp): ApplicationComponent =
        appComponent ?: DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(app))
            .build().also {
                appComponent = it
            }

    fun getMainActivityComponent(activity: AppCompatActivity): HomeActivityComponent =
        homeActivityComponent ?: getAppComponent(activity.application as AndroidMobileTestApp)
            .mainActivityComponent(ActivityModule(activity)).also {
                homeActivityComponent = it
            }

    fun getSplashActivityComponent(activity: AppCompatActivity): SplashActivityComponent =
        splashActivityComponent ?: getAppComponent(activity.application as AndroidMobileTestApp)
            .splashActivityComponent(ActivityModule(activity)).also {
                splashActivityComponent = it
            }

    fun getCharactersFragmentComponent(activity: AppCompatActivity): CharacterListFragmentComponent =
        getMainActivityComponent(activity).charactersFragmentComponent()

    fun getCharacterDetailFragmentComponent(activity: AppCompatActivity): CharacterDetailFragmentComponent =
        getMainActivityComponent(activity).characterDetailFragmentComponent()

    fun getSplashFragmentComponent(activity: AppCompatActivity): SplashFragmentComponent =
        getSplashActivityComponent(activity).splashFragmentComponent()
}