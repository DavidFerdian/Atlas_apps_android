package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponseError

data class OutletResponse(

	@field:SerializedName("data")
	val data: List<DataItemoutlet>? = null,

	@field:SerializedName("error")
	val error: DefaultResponseError? = null
)

data class DataItemoutlet(

	@field:SerializedName("out_address")
	val outAddress: String? = null,

	@field:SerializedName("out_name")
	val outName: String? = null,

	@field:SerializedName("out_code")
	val outCode: String? = null,

	@field:SerializedName("out_partnerid")
	val outPartnerid: Int? = null,

	@field:SerializedName("out_deskripsi")
	val outDeskripsi: String? = null,

	@field:SerializedName("out_companyid")
	val outCompanyid: Int? = null,

	@field:SerializedName("out_city")
	val outCity: String? = null,

	@field:SerializedName("out_longitude")
	val outLongitude: Double? = null,

	@field:SerializedName("out_areamanager")
	val outAreamanager: String? = null,

	@field:SerializedName("out_comco")
	val outComco: String? = null,

	@field:SerializedName("out_areamanagerphone")
	val outAreamanagerphone: String? = null,

	@field:SerializedName("out_subdistrict")
	val outSubdistrict: String? = null,

	@field:SerializedName("out_latitude")
	val outLatitude: Double? = null,

	@field:SerializedName("out_timeopen")
	val outTimeopen: String? = null,

	@field:SerializedName("out_province")
	val outProvince: String? = null,

	@field:SerializedName("out_timeclose")
	val outTimeclose: String? = null,

	@field:SerializedName("out_phone")
	val outPhone: String? = null,

	@field:SerializedName("out_company")
	val outCompany: String? = null,

	@field:SerializedName("Location")
	val location: Location? = null
)



data class Location(

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)
