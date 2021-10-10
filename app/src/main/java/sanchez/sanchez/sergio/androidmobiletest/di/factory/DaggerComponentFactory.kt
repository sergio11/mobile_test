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
    private var charactersFragmentComponent: CharacterListFragmentComponent? = null
    private var characterDetailFragmentComponent: CharacterDetailFragmentComponent? = null
    private var splashFragmentComponent: SplashFragmentComponent? = null

    fun getAppComponent(app: AndroidMobileTestApp): ApplicationComponent =
        appComponent ?: DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(app))
            .build().also {
                appComponent = it
            }

    fun getHomeActivityComponent(activity: AppCompatActivity): HomeActivityComponent =
        homeActivityComponent ?: getAppComponent(activity.application as AndroidMobileTestApp)
            .homeActivityComponent(ActivityModule(activity)).also {
                homeActivityComponent = it
            }

    fun removeMainActivityComponent() {
        homeActivityComponent = null
        charactersFragmentComponent = null
        characterDetailFragmentComponent = null
    }

    fun getSplashActivityComponent(activity: AppCompatActivity): SplashActivityComponent =
        splashActivityComponent ?: getAppComponent(activity.application as AndroidMobileTestApp)
            .splashActivityComponent(ActivityModule(activity)).also {
                splashActivityComponent = it
            }

    fun removeSplashActivityComponent() {
        splashActivityComponent = null
        splashFragmentComponent = null
    }

    fun getCharactersFragmentComponent(activity: AppCompatActivity): CharacterListFragmentComponent =
        charactersFragmentComponent ?: getHomeActivityComponent(activity).charactersFragmentComponent().also {
            charactersFragmentComponent = it
        }

    fun removeCharactersFragmentComponent() {
        charactersFragmentComponent = null
    }

    fun getCharacterDetailFragmentComponent(activity: AppCompatActivity): CharacterDetailFragmentComponent =
        characterDetailFragmentComponent ?: getHomeActivityComponent(activity).characterDetailFragmentComponent().also {
            characterDetailFragmentComponent = it
        }

    fun removeCharacterDetailFragmentComponent() {
        characterDetailFragmentComponent = null
    }

    fun getSplashFragmentComponent(activity: AppCompatActivity): SplashFragmentComponent =
        splashFragmentComponent ?: getSplashActivityComponent(activity).splashFragmentComponent().also {
            splashFragmentComponent = it
        }

    fun removeSplashFragmentComponent() {
        splashFragmentComponent = null
    }
}