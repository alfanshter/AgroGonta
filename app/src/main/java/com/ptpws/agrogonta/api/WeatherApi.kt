package com.ptpws.agrogonta.api

import com.ptpws.agrogonta.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getWeather(
        @Header("key") apikey: String,
        @Query("q") location: String
    ): Response<WeatherResponse>

}