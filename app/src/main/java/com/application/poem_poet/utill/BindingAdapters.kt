package com.application.poem_poet.utill

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.application.poem_poet.R


@BindingAdapter(value = ["vibrationEnabled", "soundEnabled", "listener"], requireAll = false)
fun View.bindOnClick(
    vibrationEnabled: Boolean,
    soundEnabled: Boolean,
    listener: View.OnClickListener
) {
    setOnClickListener {
        if (vibrationEnabled) {
            val vibration = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibration.vibrate(
                    VibrationEffect.createOneShot(
                        100L,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            else vibration.vibrate(100L)
        }
        if (soundEnabled) {
        }
        listener.onClick(it)
    }
}

@BindingAdapter("onKeyListener")
fun AppCompatEditText.bindOnKeyListener(
    onKeyListener: View.OnKeyListener
) {
    setOnKeyListener(onKeyListener)
}
