package sanchez.sanchez.sergio.androidmobiletest.ui.features.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.di.component.splash.SplashFragmentComponent
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.androidmobiletest.ui.features.home.HomeActivity
import sanchez.sanchez.sergio.androidmobiletest.ui.core.SupportFragment
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.navigateAndFinish

/**
 * Splash Fragment
 */
class SplashFragment: SupportFragment<SplashViewModel>(SplashViewModel::class.java)   {

    private val component: SplashFragmentComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerComponentFactory.getSplashFragmentComponent(requireActivity() as AppCompatActivity)
    }

    override fun layoutId(): Int = R.layout.splash_fragment_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.sessionState.observe(this, {
            when(it) {
                is SessionState.OnLoaded -> onSessionLoaded()
                is SessionState.OnNotFound -> onSessionNotFound()
            }
        })
    }

    override fun onInject() {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.loadSession()
        }, LOAD_SESSION_DELAY)
    }

    /**
     * Private Methods
     */

    private fun onSessionLoaded() {
        navigateAndFinish(HomeActivity.createDestination(requireActivity()))
    }

    private fun onSessionNotFound() {
        // TODO: go to login screen
    }

    companion object {

        private val LOAD_SESSION_DELAY = 5000L

    }
}