package com.example.atlas.APIRequestHandling

import com.example.atlas.DataClass.CategoriesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesAPI {


    //Get Categories List
    //Endpoint: https://staging-api.cfu.pharmalink.id/atlas/get?tampil=menu&tipe=All&groupID=asd
    @GET("get?tampil=menu&tipe=All&groupID=GK22011")
    fun getCategoriesList(): Call<CategoriesListResponse>

}