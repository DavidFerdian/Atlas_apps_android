package com.example.atlas.APIRequestHandling

import com.example.atlas.DataClass.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MemberAPI {


    //Get Categories List
    //Endpoint: localhost:8888/atlas/member?insert=member
    @POST("member?insert=member")
    fun RegisterMember(
        @Body postedInformation: RegisterRequest
    ): Call<RegisterMemberResponse>

    //Login service
    //Endpoint: localhost:8888/atlas/member?login
    @POST("member?login")
    fun loginUser(
        @Body postedInformation: LoginRequestBody
    ): Call<LoginResponse>

    //Get Profile-related data of currently logged in user
    //Endpoint: https://staging-api.cfu.pharmalink.id/atlas/member?tampil=profile&memberID=820080002
    @GET("member?tampil=profile")
    fun getMemberDataByMemberId(@Query("memberID") ememberID: String): Call<Profile>

}