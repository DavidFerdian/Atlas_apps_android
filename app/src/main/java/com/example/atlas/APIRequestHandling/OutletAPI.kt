package com.example.atlas.APIRequestHandling

import com.example.atlas.DataClass.OutletBody
import com.example.atlas.DataClass.OutletResponse
import com.example.atlas.DataClass.RegisterMemberResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OutletAPI {

    //Get Outlet List
    //Endpoint: https://staging-api.cfu.pharmalink.id/atlas/post?search=outlet
    @POST("post?search=outlet")
    fun getOutletList(
        @Body postedInformation: OutletBody
    ): Call<OutletResponse>

}