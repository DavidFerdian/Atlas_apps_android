package com.example.atlas.APIRequestHandling

import android.content.Context
import android.util.Log
import com.example.atlas.Util.LoginStatusPreference
import okhttp3.Interceptor
import okhttp3.Response

class TokenRenewInterceptor(context: Context) : Interceptor {
    //Shared Preference to save login informations (ID, Token, etc)
    private val loginPreference = LoginStatusPreference(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if(loginPreference.getUserLoggedStatus().isLoggedIn == true){
            val freshToken = response.headers["authorization"]
            val currentToken = loginPreference.getLoginToken()
            Log.i("usedToken", "Returned with code ${response.code} --- ${response.message}")

            if (freshToken != "" && freshToken?.contains(" CENTURY") == false) {
                if (freshToken != currentToken) {
                    Log.i("usedToken", "Token replaced to $freshToken")
                    loginPreference.setToken(freshToken)
                }
            }
        }else{
            Log.i("usedToken", "No user logged in")
        }



        return response
    }
}