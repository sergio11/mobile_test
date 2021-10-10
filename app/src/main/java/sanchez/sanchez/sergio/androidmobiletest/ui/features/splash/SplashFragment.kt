package sanchez.sanchez.sergio.androidmobiletest.ui.features.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import sanchez.sanchez.sergio.androidmobiletest.databinding.SplashFragmentLayoutBinding
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.androidmobiletest.ui.features.home.HomeActivity
import sanchez.sanchez.sergio.androidmobiletest.ui.core.SupportFragment
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.navigateAndFinish

/**
 * Splash Fragment
 */
class SplashFragment: SupportFragment<SplashViewModel, SplashFragmentLayoutBinding>(SplashViewModel::class.java)   {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SplashFragmentLayoutBinding =
        SplashFragmentLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.loadSession()
        }, LOAD_SESSION_DELAY)
    }

    override fun onAttachComponent() {
        DaggerComponentFactory.getSplashFragmentComponent(requireActivity() as AppCompatActivity)
            .inject(this)
    }

    override fun onDetachComponent() {
        DaggerComponentFactory.removeSplashFragmentComponent()
    }

    override fun onInitObservers() {
       lifecycleScope.launchWhenStarted {
           viewModel.sessionState.observe(this@SplashFragment, {
               when(it) {
                   is SessionState.OnLoaded -> onSessionLoaded()
                   is SessionState.OnNotFound -> onSessionNotFound()
               }
           })
       }
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