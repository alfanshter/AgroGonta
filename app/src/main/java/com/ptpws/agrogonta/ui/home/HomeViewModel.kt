package com.ptpws.agrogonta.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptpws.agrogonta.data.models.WeatherResponse
import com.ptpws.agrogonta.data.repo.WeatherRepository
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val penyiramanRepository: PenyiramanRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel(){

    private val _weatherState = MutableStateFlow<Resource<WeatherResponse>?>(null)
    val weatherState: StateFlow<Resource<WeatherResponse>?> = _weatherState

    val gh: StateFlow<GhModel> get() = penyiramanRepository.gh
//

    init {
        viewModelScope.launch {
            penyiramanRepository.getGh()
        }
    }

    // Function to fetch news
    fun fetchWeather(location : String) {
        viewModelScope.launch {
            _weatherState.value = Resource.Loading// Set loading state
            val result = weatherRepository.getWeather(location) // Call the repository function
            _weatherState.value = result // Update the state with the result
        }
    }




}