package com.example.atlas.DataClass


import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponseError
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: LoginResponseData? = null,

    @field:SerializedName("error")
    val error: DefaultResponseError? = null
)

data class LoginResponseData(

    @field:SerializedName("emember_id")
    val ememberId: String? = null,

    @field:SerializedName("emember_username")
    val ememberUsername: String? = null,

    @field:SerializedName("emember_gender")
    val ememberGender: String? = null,

    @field:SerializedName("emember_name")
    val ememberName: String? = null,

    @field:SerializedName("emember_dob")
    val ememberDob: String? = null,

    @field:SerializedName("emember_nohp")
    val ememberNohp: String? = null,

    @field:SerializedName("emember_tc_version")
    val ememberTcVersion: String? = null,

    @field:SerializedName("emember_tc_yn")
    val ememberTcYN: String? = null,

    @field:SerializedName("emember_old_yn")
    val ememberOldYN: String? = null,

    @field:SerializedName("emember_email")
    val ememberEmail: String? = null,

    @field:SerializedName("emember_address")
    val ememberAddress: String? = null
)

