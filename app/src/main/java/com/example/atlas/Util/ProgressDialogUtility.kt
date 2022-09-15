package com.example.atlas.Util
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.atlas.R


object ProgressDialogUtility {

    lateinit var progressDialog: AlertDialog

    fun showProgressDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false) // if you want user to wait for some process to finish,

        builder.setView(R.layout.layout_progress_dialog)
        progressDialog = builder.create()
        progressDialog.show()
    }

    fun dismissProgressDialog() {
        try {
            progressDialog.dismiss()
        } catch (error: Exception) {
            //Nothing to do
        }
    }

}