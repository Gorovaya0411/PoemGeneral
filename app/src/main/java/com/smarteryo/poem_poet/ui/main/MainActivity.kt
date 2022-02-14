package com.smarteryo.poem_poet.ui.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.smarteryo.poem_poet.MyApplication
import com.smarteryo.poem_poet.R
import com.smarteryo.poem_poet.di.modul.ui.main.MainActivityModule
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun provideMainActivityPresenter(): MainActivityPresenter {
        return MyApplication.appComponent.with(MainActivityModule()).presenter
    }

    private lateinit var navController: NavController
    private val vibrator: Vibrator by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        goToWelcomeFragment()
    }

    override fun goToWelcomeFragment() {
        navController.navigate(R.id.action_splashFragment_to_welcomeFragment)
    }

    override fun goToOptionFragment() {
//        navController.navigate(R.id.action_menuFragment_to_optionFragment)
    }

    fun soundCheckAndStartVibration() {
        if (presenter.getCheckSound()) {

        }
        startVibration()
    }

    private fun startVibration(milliseconds: Long = 200) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    milliseconds,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        else vibrator.vibrate(milliseconds)
    }
}
