package com.example.atlas.Util

import android.content.Context
import com.example.atlas.DashboardBottomNavigationPreference


object SharedPreferenceUtility {

    fun resetDashboardBottomNavPosition(context: Context) {
        val dashboardBottomNavigationPreference =
            DashboardBottomNavigationPreference(
                context
            )
        dashboardBottomNavigationPreference.clearLastSelectedFragmentInformation()
    }

    fun clearLoggedUser(context: Context) {
        val loggedStatusPreference =
            LoginStatusPreference(
                context
            )

        if (loggedStatusPreference.loginStatusPreference.contains(LoginStatusPreference.LOGGED_MEMBER_ID)) {
            val loggedStatus = loggedStatusPreference.getUserLoggedStatus()

            loggedStatus.isLoggedIn = false
            loggedStatus.loggedInUserName = null
            loggedStatus.loggedInMemberId = null

            loggedStatusPreference.setAsLoggedOut()
        }
    }

    fun getLoggedMemberID(context: Context): String {
        val loggedStatusPreference =
            LoginStatusPreference(
                context
            )

        val loggedStatus = loggedStatusPreference.getUserLoggedStatus()

        return loggedStatus.loggedInMemberId.toString()
    }

    fun getLoggedUsername(context: Context): String{
        val loggedStatusPreference =
            LoginStatusPreference(
                context
            )

        val loggedStatus = loggedStatusPreference.getUserLoggedStatus()

        return loggedStatus.loggedInUserName.toString()
    }
}