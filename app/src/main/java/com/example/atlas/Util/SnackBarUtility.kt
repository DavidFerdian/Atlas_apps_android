package com.id.centuryememberproject.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarUtility {

    fun renderSnackBar(baseLayout: View?, message: String) {
        val snackbar = baseLayout?.let {
            Snackbar.make(
                it,
                message, Snackbar.LENGTH_LONG
            )
        }

        snackbar?.setAction("Tutup") {
            snackbar.dismiss()
        }
        snackbar?.show()
    }
}