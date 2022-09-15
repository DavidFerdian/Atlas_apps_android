package com.example.atlas.APIRequestHandling

import com.example.atlas.DataClass.CategoriesListResponse
import com.example.atlas.DataClass.ProductResponseList
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {


    //Get Categories List
    //Endpoint: hhttps://staging-api.cfu.pharmalink.id/atlas/product
    @GET("atlas/get?tampil=menu&tipe=All&groupID=asd")
    fun getProductList(): Call<ProductResponseList>
}