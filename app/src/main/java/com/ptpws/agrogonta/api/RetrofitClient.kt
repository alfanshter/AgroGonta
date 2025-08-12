package com.ptpws.agrogonta.api

import com.ptpws.agrogonta.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://example.com/api/" // ganti sesuai API kamu

    val instance: ModeApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.MODE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ModeApi::class.java)
    }
}