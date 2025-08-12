package com.ptpws.agrogonta.data.models

import com.google.gson.annotations.SerializedName

data class ModeResponse(

	@field:SerializedName("mode")
	val mode: String,

	@field:SerializedName("status")
	val status: String
)
