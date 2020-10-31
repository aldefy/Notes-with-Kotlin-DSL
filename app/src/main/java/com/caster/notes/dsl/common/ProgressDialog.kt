package com.caster.notes.dsl.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.caster.notes.dsl.R

class ProgressDialog(val context: Context) {
    private val dialog = Dialog(context)

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
    }

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.hide()
    }
}