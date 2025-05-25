package com.ptpws.agrogonta.data.models

import com.google.gson.annotations.SerializedName

data class CurrentModel(

	@field:SerializedName("feelslike_c")
	val feelslikeC: Any? = null,

	@field:SerializedName("feelslike_f")
	val feelslikeF: Any? = null,

	@field:SerializedName("wind_degree")
	val windDegree: Int? = null,

	@field:SerializedName("windchill_f")
	val windchillF: Any? = null,

	@field:SerializedName("windchill_c")
	val windchillC: Any? = null,

	@field:SerializedName("last_updated_epoch")
	val lastUpdatedEpoch: Int? = null,

	@field:SerializedName("temp_c")
	val tempC: Any? = null,

	@field:SerializedName("temp_f")
	val tempF: Any? = null,

	@field:SerializedName("cloud")
	val cloud: Int? = null,

	@field:SerializedName("wind_kph")
	val windKph: Any? = null,

	@field:SerializedName("wind_mph")
	val windMph: Any? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("dewpoint_f")
	val dewpointF: Any? = null,

	@field:SerializedName("uv")
	val uv: Any? = null,

	@field:SerializedName("last_updated")
	val lastUpdated: String? = null,

	@field:SerializedName("heatindex_f")
	val heatindexF: Any? = null,

	@field:SerializedName("dewpoint_c")
	val dewpointC: Any? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("precip_in")
	val precipIn: Any? = null,

	@field:SerializedName("heatindex_c")
	val heatindexC: Any? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("gust_mph")
	val gustMph: Any? = null,

	@field:SerializedName("pressure_in")
	val pressureIn: Any? = null,

	@field:SerializedName("gust_kph")
	val gustKph: Any? = null,

	@field:SerializedName("precip_mm")
	val precipMm: Any? = null,

	@field:SerializedName("condition")
	val condition: ConditionModel? = null,

	@field:SerializedName("vis_km")
	val visKm: Any? = null,

	@field:SerializedName("pressure_mb")
	val pressureMb: Any? = null,

	@field:SerializedName("vis_miles")
	val visMiles: Any? = null
)