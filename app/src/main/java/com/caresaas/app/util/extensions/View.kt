package com.caresaas.app.util.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun View.visibleIf(show: Boolean?) {
    visibility = if (show == true) View.VISIBLE
    else View.GONE
}

fun View.setColor(check: Boolean?, @ColorRes checkedColor: Int, @ColorRes uncheckedColor: Int) {
    if (check == true) {
        setBackgroundColor(ContextCompat.getColor(context, checkedColor))
    } else setBackgroundColor(ContextCompat.getColor(context, uncheckedColor))
}

fun View.setBkg(check: Boolean?, @DrawableRes checkedDrawable: Int, @DrawableRes uncheckedDrawable: Int) {
    background = if (check == true) {
        ContextCompat.getDrawable(context, checkedDrawable)
    } else ContextCompat.getDrawable(context, uncheckedDrawable)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}