package sanchez.sanchez.sergio.androidmobiletest.ui.features.splash

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.databinding.ActivitySplashScreenBinding
import sanchez.sanchez.sergio.androidmobiletest.di.component.SplashActivityComponent
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory

/**
 * Splash Screen Activity
 */
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerComponentFactory.getSplashActivityComponent(this)
            .inject(this)
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        with(binding) {

            val animDrawable = container.background as AnimationDrawable
            animDrawable.apply {
                setEnterFadeDuration(10)
                setExitFadeDuration(5000)
                start()
            }

            AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.splash_stripe_anim).let {
                stripes.startAnimation(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DaggerComponentFactory.removeSplashActivityComponent()
    }
}