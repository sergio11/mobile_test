package sanchez.sanchez.sergio.androidmobiletest.di.component

import dagger.Subcomponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.character.CharacterDetailFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.character.CharacterListFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.component.core.ActivityComponent
import sanchez.sanchez.sergio.androidmobiletest.di.modules.core.ActivityModule
import sanchez.sanchez.sergio.androidmobiletest.di.scopes.PerActivity
import sanchez.sanchez.sergio.androidmobiletest.ui.features.home.HomeActivity


@PerActivity
@Subcomponent(modules = [
    ActivityModule::class
])
interface HomeActivityComponent: ActivityComponent {

    fun inject(activity: HomeActivity)

    fun charactersFragmentComponent(): CharacterListFragmentComponent

    fun characterDetailFragmentComponent(): CharacterDetailFragmentComponent

}