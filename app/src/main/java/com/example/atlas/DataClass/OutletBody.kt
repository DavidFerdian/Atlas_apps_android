package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName

data class OutletBody(

	@field:SerializedName("lon")
	val lon: Float? = null,

	@field:SerializedName("lat")
	val lat: Float? = null
)
