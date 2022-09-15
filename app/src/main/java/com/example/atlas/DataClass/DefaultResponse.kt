package com.id.centuryememberproject.dataClass.defaultResponse

import com.google.gson.annotations.SerializedName

data class DefaultResponse(

    @field:SerializedName("error")
    val error: DefaultResponseError? = null
)

data class DefaultResponseError(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("status")
    val status: Boolean
)
