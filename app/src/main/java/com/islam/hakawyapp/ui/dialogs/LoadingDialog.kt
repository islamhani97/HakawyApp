package com.islam.hakawyapp.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.islam.hakawyapp.R
import com.islam.hakawyapp.databinding.DialogLoadingBinding

class LoadingDialog(private val context: Context) {

    private var binding: DialogLoadingBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_loading,
        null,
        false
    )
    private var alertDialog: AlertDialog =  MaterialAlertDialogBuilder(context, R.style.DialogTheme)
        .setView(binding.root)
        .setCancelable(false)
        .setBackground(ColorDrawable(Color.TRANSPARENT))
        .create()



    fun show() {
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    fun dismiss() {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }


}