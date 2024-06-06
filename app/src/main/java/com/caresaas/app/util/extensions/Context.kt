package com.caresaas.app.util.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.customview.customView

fun Context.showDialog(
    title: String = "Error",
    message: String,
    positiveTitle: String = "ok",
    negativeTitle: String? = "",
    dismiable: Boolean = false,
    negativeCallback: (() -> Unit)? = null,
    positiveCallback: (() -> Unit)? = null
) {

    MaterialDialog(this).show {
        cornerRadius(5F)
        title(text = title)
        message(text = message)
        cancelable(dismiable)

        positiveButton(text = positiveTitle) { _ ->
           positiveCallback?.invoke()
        }

        negativeButton(text = negativeTitle) {
            negativeCallback?.invoke()
            dismiss()
        }
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

