package com.application.poem_poet.dialogFragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.application.poem_poet.R

class ForOutDialog(private val callback: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val builder = AlertDialog.Builder(
            requireContext(), R.style.ThemeOverlay_AppCompat_Dialog_Alert_TestDialogTheme
        )

        builder.setMessage("Вы точно хотите выйти?")
            .setPositiveButton("ДА") { _, _ ->
                callback.invoke()
            }.setNegativeButton("Нет..") { _, _ ->
                dialog!!.dismiss()
            }

        return builder.create()
    }
}