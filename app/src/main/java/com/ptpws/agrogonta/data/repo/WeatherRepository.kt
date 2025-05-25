package com.ptpws.agrogonta.data.repo

import com.ptpws.agrogonta.data.models.WeatherResponse
import com.ptpws.agrogontafarm.data.Resource
import okhttp3.MultipartBody


interface WeatherRepository {
    suspend fun getWeather(location :String): Resource<WeatherResponse>
}

