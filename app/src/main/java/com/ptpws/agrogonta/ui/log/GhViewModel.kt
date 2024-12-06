package com.ptpws.agrogontafarm.ui.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptpws.agrogonta.data.penyiraman.DummyPenyiramanRepository
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.models.PenyiramanModel
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GhViewModel @Inject constructor(
    private val penyiramanRepository: PenyiramanRepository
) : ViewModel(){

    val gh: StateFlow<GhModel> get() = penyiramanRepository.gh
//

    private val _setPenyiraman = MutableStateFlow<Resource<Unit>?>(null)
    val setPenyiraman: StateFlow<Resource<Unit>?> = _setPenyiraman

    private val _setPengaduk = MutableStateFlow<Resource<Unit>?>(null)
    val setPengaduk: StateFlow<Resource<Unit>?> = _setPengaduk

    private val _setSaluran = MutableStateFlow<Resource<Unit>?>(null)
    val setSaluran: StateFlow<Resource<Unit>?> = _setSaluran

    private val _setPenyiramanOtomatis = MutableStateFlow<Resource<Unit>?>(null)
    val setPenyiramanOtomatis: StateFlow<Resource<Unit>?> = _setPenyiramanOtomatis


    init {
        viewModelScope.launch {
            penyiramanRepository.getGh()
        }
    }

    fun setsiram(data : String, nilai : Int) = viewModelScope.launch {
        _setPenyiraman.value = Resource.Loading
        val result = penyiramanRepository.setPenyiraman(data,nilai)
        _setPenyiraman.value = result
    }


    fun setPengaduk(data : String, nilai : Int) = viewModelScope.launch {
        _setPengaduk.value = Resource.Loading
        val result = penyiramanRepository.setPengaduk(data,nilai)
        _setPengaduk.value = result
    }


    fun setSaluran(data : String, nilai : Int) = viewModelScope.launch {
        _setSaluran.value = Resource.Loading
        val result = penyiramanRepository.setSaluran(data,nilai)
        _setSaluran.value = result
    }




}