package com.ptpws.agrogonta.data.models

import com.google.gson.annotations.SerializedName

data class ConditionModel(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)