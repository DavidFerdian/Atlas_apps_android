package com.example.atlas.Util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.Display


object DeviceDimensionUtility {
    fun getScreenWidth(context: Context, activity: Activity?): Int {

        val displayMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = (context).display
            display?.getRealMetrics(displayMetrics)

            return displayMetrics.widthPixels
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(displayMetrics)

            return displayMetrics.widthPixels
        }
    }

    fun getScreenHeight(context: Context, activity: Activity?): Int {
        val displayMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = (context).display
            display?.getRealMetrics(displayMetrics)

            return displayMetrics.heightPixels
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(displayMetrics)

            return displayMetrics.heightPixels
        }
    }
}