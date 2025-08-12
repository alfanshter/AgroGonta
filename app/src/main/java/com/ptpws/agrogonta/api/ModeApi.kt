package com.ptpws.agrogonta.api

import com.ptpws.agrogonta.data.models.ModeResponse
import com.ptpws.agrogonta.data.models.WeatherResponse
import com.ptpws.agrogonta.data.models.request.ModeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ModeApi {

    @GET("mode")
    suspend fun getMode(): Response<ModeResponse>

    @POST("mode")
    suspend fun setMode(
        @Body modeRequest: ModeRequest
    ): Response<ModeResponse>
}