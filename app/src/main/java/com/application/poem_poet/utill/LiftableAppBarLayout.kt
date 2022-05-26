package com.application.poem_poet.utill

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.R
import com.google.android.material.appbar.AppBarLayout

open class LiftableAppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.appBarLayoutStyle
) : AppBarLayout(context, attrs, defStyleAttr) {
    private var isElevated = false

    fun elevate(elevate: Boolean) {
        if (elevate == isElevated) return
        isElevated = elevate
        val elevation = if (elevate) 4f else 0f
        animate()
            .translationZ(elevation)
            .apply { duration = ELEVATION_DURATION_MS }
            .start()
    }

    companion object {
        private const val ELEVATION_DURATION_MS = 200L
    }
}
