package sanchez.sanchez.sergio.androidmobiletest.ui.features.home

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.createDestination

/**
 * Home Activity
 */
class HomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerComponentFactory.getHomeActivityComponent(this)
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        DaggerComponentFactory.removeMainActivityComponent()
    }

    companion object {

        @JvmStatic
        fun createDestination(activity: Activity): ActivityNavigator.Destination =
            activity.createDestination(HomeActivity::class.java)
    }

}