package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponseError

data class RegisterMemberResponse(

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("error")
    val error: DefaultResponseError
)

