package com.example.pinterest.Helper

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.pinterest.R

object ProgressDialog {
    lateinit var dialog: Dialog

    fun showProgress(context: Context) {
        dialog = Dialog(context)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun dismissProgress() {
        dialog.dismiss()
    }
}