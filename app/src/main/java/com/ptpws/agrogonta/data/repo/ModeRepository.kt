package com.ptpws.agrogonta.data.repo

import com.ptpws.agrogonta.data.models.ModeResponse
import com.ptpws.agrogonta.data.models.WeatherResponse
import com.ptpws.agrogontafarm.data.Resource

interface ModeRepository {
    suspend fun getMode(): Resource<ModeResponse>
    suspend fun setMode(mode: String): Resource<ModeResponse>
}

