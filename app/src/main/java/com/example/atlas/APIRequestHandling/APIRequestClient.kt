package com.example.atlas.APIRequestHandling

import android.content.Context
import com.example.atlas.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Member
import java.util.concurrent.TimeUnit

object APIRequestClient {
    private const val BASE_URL = BuildConfig.BASE_URL_ATLAS + "atlas/"

    private fun customClientWithTokenRenew(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(TokenRenewInterceptor(context))
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    private fun customClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun CategoriesService(): CategoriesAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(customClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CategoriesAPI::class.java)
    }

    fun MemberService(context: Context): MemberAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(customClient())
            .addConverterFactory(GsonConverterFactory.create())
            .client(customClientWithTokenRenew(context))
            .build()

        return retrofit.create(MemberAPI::class.java)
    }

    fun outletService(context: Context): OutletAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(customClient())
            .addConverterFactory(GsonConverterFactory.create())
            .client(customClientWithTokenRenew(context))
            .build()

        return retrofit.create(OutletAPI::class.java)
    }
}