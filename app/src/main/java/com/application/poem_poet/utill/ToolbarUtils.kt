package com.application.poem_poet.utill

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.application.poem_poet.R
import java.util.concurrent.atomic.AtomicBoolean

class ToolbarUtils {

    companion object {

        private val isMenuIsUp = AtomicBoolean(false)

        fun setSupportActionBar(context: Context, toolbar: Toolbar) {
            try {
                (context as AppCompatActivity).setSupportActionBar(toolbar)
            } catch (exc: java.lang.ClassCastException) {
                // Ignore AS editor exception
            }
        }

        fun setTransparentStatusBar(activity: Activity, layoutNoLimits: Boolean = true) {
            activity.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                if (layoutNoLimits) addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = ContextCompat.getColor(context, R.color.transparent)
            }
        }

        @Synchronized
        fun setToolbarHomeAsUp(context: Context, @DrawableRes iconId: Int? = null) {
            if (context is AppCompatActivity) {
                getSupportActionBar(context)?.also { actionBar ->
                    iconId?.let {
                        actionBar.setHomeAsUpIndicator(it)
                    } ?: run {
                        actionBar.setHomeAsUpIndicator(null)
                    }
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
                isMenuIsUp.set(true)
            }
        }

        private fun getSupportActionBar(context: AppCompatActivity): ActionBar? {
            return context.supportActionBar
        }
    }
}
