package com.ptpws.agrogonta.data.repo

import android.util.Log
import com.ptpws.agrogonta.api.ModeApi
import com.ptpws.agrogonta.api.WeatherApi
import com.ptpws.agrogonta.data.models.ModeResponse
import com.ptpws.agrogonta.data.models.WeatherResponse
import com.ptpws.agrogonta.data.models.request.ModeRequest
import com.ptpws.agrogonta.utils.Constant
import com.ptpws.agrogontafarm.data.Resource
import javax.inject.Inject

class ModeRepositoryImpl @Inject constructor(
    private val modeApi: ModeApi
): ModeRepository{

    override suspend fun getMode(): Resource<ModeResponse> {
        return try {
            // Perform the API call
            val response = modeApi.getMode()
            Log.d("dinda", "getWeather: $response")
            // Check the response status (this may depend on your API structure)
            if (response.isSuccessful) {
                // If the response is successful, return Resource.Success with the data
                println("dinda ${response.body()}")
                Resource.Success(response.body()!!)
            } else {
                // If the response indicates an error, return Resource.Error with an error message
                println("dinda error ${response.body()}")

                Resource.Error("Error: ${response.message()}")

            }
        } catch (e: Exception) {
            // Handle any exceptions that occurred during the API call
            println("dinda catch ${e.message}")

            Resource.Error("Exception: ${e.message}")
        }
    }

    override suspend fun setMode(mode: String): Resource<ModeResponse> {
        return try {
            val request = ModeRequest(mode)
            val response = modeApi.setMode(request)
            if (response.isSuccessful) {
                println("dinda done")
                Resource.Success(response.body()!!)
            } else {
                println("dinda error ${response.message()}")
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            println("dinda error ${e.message}")

            Resource.Error("Exception: ${e.message}")
        }
    }

}