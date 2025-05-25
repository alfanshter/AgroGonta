package com.ptpws.agrogontafarm.ui.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptpws.agrogonta.ui.log.penyiraman.convertTimestamp
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.models.PenyiramanAutoModel
import com.ptpws.agrogontafarm.data.models.PenyiramanModel
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PenyiramanViewModel @Inject constructor(
    private val penyiramanRepository: PenyiramanRepository
) : ViewModel(){

    val penyiraman: StateFlow<PenyiramanModel> get() = penyiramanRepository.penyiraman

    val gh: StateFlow<GhModel> get() = penyiramanRepository.gh

    private val _setPenyiraman = MutableStateFlow<Resource<Unit>?>(null)
    val setPenyiraman: StateFlow<Resource<Unit>?> = _setPenyiraman

    private val _setFlushing = MutableStateFlow<Resource<Unit>?>(null)
    val setFlushing: StateFlow<Resource<Unit>?> = _setFlushing

    private val _modePenyiraman = MutableStateFlow<Resource<String>>(Resource.Loadings())
    val modePenyiraman: StateFlow<Resource<String>> get() = _modePenyiraman


    private val _setPenyiramanOtomatis = MutableStateFlow<Resource<String>?>(null)
    val setPenyiramanOtomatis: StateFlow<Resource<String>?> = _setPenyiramanOtomatis

    private val _deletePenyiramanOtomatis = MutableStateFlow<Resource<Unit>?>(null)
    val deletePenyiramanOtomatis: StateFlow<Resource<Unit>?> = _deletePenyiramanOtomatis

    private val _setMode = MutableStateFlow<Resource<Unit>?>(null)
    val setMode: StateFlow<Resource<Unit>?> = _setMode

    private val _penyiramanOtomatis = MutableStateFlow<Resource<List<PenyiramanAutoModel>>?>(null)
    val penyiramanOtomatis: StateFlow<Resource<List<PenyiramanAutoModel>>?> = _penyiramanOtomatis

    private val _updateStatus = MutableStateFlow<Resource<Unit>?>(null)
    val updateStatus: StateFlow<Resource<Unit>?> = _updateStatus

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        viewModelScope.launch {
            penyiramanRepository.getPenyiraman()
            fetchModePenyiraman()
            getPenyiramanOtomatis()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            penyiramanRepository.getPenyiraman()
            fetchModePenyiraman()
            getPenyiramanOtomatis()
            _isRefreshing.value = false
        }
    }
    fun setsiram(data : String, nilai : Int) = viewModelScope.launch {
        _setPenyiraman.value = Resource.Loading
        val result = penyiramanRepository.setPenyiraman(data,nilai)
        _setPenyiraman.value = result
    }

    fun setPenyiramOtomatis(waktu : Long, duration : Long, status : Boolean) = viewModelScope.launch {
        _setPenyiramanOtomatis.value = Resource.Loading
        val result = penyiramanRepository.setPenyiramanOtomatis(waktu,duration,status)
        _setPenyiramanOtomatis.value = result
    }

    fun setflushing(data : String, nilai : Int) = viewModelScope.launch {
        _setFlushing.value = Resource.Loading
        val result = penyiramanRepository.setFlushing(data,nilai)
        _setFlushing.value = result
    }
    // Fungsi untuk memanggil `getMode()` di repository
    fun fetchModePenyiraman() {
        viewModelScope.launch {
            _modePenyiraman.value = Resource.Loadings() // Set status loading
            val result = penyiramanRepository.getMode()
            _modePenyiraman.value = result // Emit hasil dari repository
        }
    }

    fun setMode(status : String) = viewModelScope.launch {
        _setMode.value = Resource.Loading
        val result = penyiramanRepository.setMode(status)
        _setMode.value = result
    }

    fun getPenyiramanOtomatis() {
        viewModelScope.launch {
            _penyiramanOtomatis.value = Resource.Loading// Set loading state
            val result = penyiramanRepository.getPenyiramanOtomatis() // Call the repository function
            _penyiramanOtomatis.value = result // Update the state with the result
        }
    }

    fun updateStatus(status : Boolean, id : String) = viewModelScope.launch {
        _updateStatus.value = Resource.Loading
        val result = penyiramanRepository.updateStatus(status,id)
        _setMode.value = result
    }

    fun deletePenyiramOtomatis(id : String) = viewModelScope.launch {
        _deletePenyiramanOtomatis.value = Resource.Loading
        val result = penyiramanRepository.deletePenyiramanOtomatis(id)
        _deletePenyiramanOtomatis.value = result
    }







}