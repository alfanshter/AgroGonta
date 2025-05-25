package com.ptpws.agrogonta.data.repo

import android.util.Log
import com.ptpws.agrogonta.api.WeatherApi
import com.ptpws.agrogonta.data.models.WeatherResponse
import com.ptpws.agrogonta.utils.Constant
import com.ptpws.agrogontafarm.data.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository{
    override suspend fun getWeather(location : String): Resource<WeatherResponse> {
        return try {
            // Perform the API call
            val response = weatherApi.getWeather(location = location, apikey = Constant.api_key)
            Log.d("dinda", "getWeather: $response")
            // Check the response status (this may depend on your API structure)
            if (response.isSuccessful) {
                // If the response is successful, return Resource.Success with the data
                Resource.Success(response.body()!!)
            } else {
                // If the response indicates an error, return Resource.Error with an error message
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            // Handle any exceptions that occurred during the API call
            Resource.Error("Exception: ${e.message}")
        }
    }

}