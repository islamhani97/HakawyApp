package com.islam.hakawyapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.islam.hakawyapp.R

class InternetReceiver(private val context: Context) : BroadcastReceiver() {

    private var alertDialog: AlertDialog =  MaterialAlertDialogBuilder(context, R.style.DialogTheme)
        .setCancelable(false)
        .setBackground(context.getDrawable(R.drawable.shape_dialog_background))
        .setTitle(R.string.error_no_internet)
        .create()

    override fun onReceive(context: Context?, intent: Intent?) {

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent!!.action) {

            val noConnection =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, true)
            if (noConnection) {
                show()
            } else {
                dismiss()
            }
        }
    }


    private fun show() {
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    private fun dismiss() {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

}