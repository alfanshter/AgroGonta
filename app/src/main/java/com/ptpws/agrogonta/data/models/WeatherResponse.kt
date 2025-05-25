package com.ptpws.agrogonta.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("current")
	val current: CurrentModel? = null,

	@field:SerializedName("location")
	val location: LocationModel? = null
)