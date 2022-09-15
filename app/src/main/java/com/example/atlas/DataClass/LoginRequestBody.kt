package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(

    @field:SerializedName("emember_username")
    val ememberUsername: String,

    @field:SerializedName("emember_password")
    val ememberPassword: String
)
