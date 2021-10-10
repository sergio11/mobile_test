package sanchez.sanchez.sergio.androidmobiletest.ui.features.splash

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.di.component.SplashActivityComponent
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory

/**
 * Splash Screen Activity
 */
class SplashScreenActivity : AppCompatActivity() {

    private val activityComponent: SplashActivityComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerComponentFactory.getSplashActivityComponent(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        val animDrawable = container.background as AnimationDrawable
        animDrawable.apply {
            setEnterFadeDuration(10)
            setExitFadeDuration(5000)
            start()
        }

        AnimationUtils.loadAnimation(this, R.anim.splash_stripe_anim).let {
            stripes.startAnimation(it)
        }
    }
}