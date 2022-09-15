package com.example.atlas

import android.content.Context
import android.util.Log
import com.example.atlas.DataClass.LastSelectedBottomNavigation

class DashboardBottomNavigationPreference(context: Context) {
    companion object {
        const val BOTTOM_NAV_STAT_PREFERENCE_NAME = "bottom_nav_status_preference"
        const val LAST_SELECTED_FRAGMENT = "last_selected_fragment"
    }

    val dashboardBottomNavPreference = context.getSharedPreferences(
        BOTTOM_NAV_STAT_PREFERENCE_NAME, Context.MODE_PRIVATE
    )


    fun setLastSelectedFragmentInformation(currentSelectedFragment: LastSelectedBottomNavigation) {
        val preferenceEditor = dashboardBottomNavPreference.edit()

        preferenceEditor.putString(
            LAST_SELECTED_FRAGMENT,
            currentSelectedFragment.lastSelectedFragmentName
        )
        preferenceEditor.apply()

        Log.i(
            "DashboardPref",
            "New Selected Fragment Applied ! (${currentSelectedFragment.lastSelectedFragmentName})"
        )
    }


    fun getLastSelectedFragmentInformation(): LastSelectedBottomNavigation {

        val lastSelectedBottomNavigationItem =
            LastSelectedBottomNavigation()

        lastSelectedBottomNavigationItem.lastSelectedFragmentName =
            dashboardBottomNavPreference.getString(
                LAST_SELECTED_FRAGMENT, null
            ).toString()

        return lastSelectedBottomNavigationItem
    }

    fun clearLastSelectedFragmentInformation() {
        val preferenceEditor = dashboardBottomNavPreference.edit()

        preferenceEditor.putString(LAST_SELECTED_FRAGMENT, null)
        preferenceEditor.apply()

        Log.i("DashboardPref", "Selected Fragment Cleared !!")

    }

}