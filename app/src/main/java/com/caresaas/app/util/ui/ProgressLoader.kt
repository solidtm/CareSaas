package com.caresaas.app.util.ui

interface ProgressLoader {
    fun show(message: String? = null, cancellable: Boolean = false)
    fun hide()
    fun isShowing(): Boolean
}