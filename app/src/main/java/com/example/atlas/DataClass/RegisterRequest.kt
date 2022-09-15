package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName

data class RegisterRequest(


	@field:SerializedName("emember_kota")
	val ememberKota: String? = null,

	@field:SerializedName("emember_kecamatan")
	val ememberKecamatan: String? = null,

	@field:SerializedName("emember_username")
	val ememberUsername: String? = null,

	@field:SerializedName("emember_kodepos")
	val ememberKodepos: String? = null,

	@field:SerializedName("emember_provinsi")
	val ememberProvinsi: String? = null,

	@field:SerializedName("emember_name")
	val ememberName: String? = null,

	@field:SerializedName("emember_password")
	val ememberPassword: String? = null,

	@field:SerializedName("emember_nohp")
	val ememberNohp: String? = null,

	@field:SerializedName("emember_email")
	val ememberEmail: String? = null,

	@field:SerializedName("emember_address")
	val ememberAddress: String? = null,

	@field:SerializedName("emember_tc_version")
	val ememberTcVersion: String? = null
)
