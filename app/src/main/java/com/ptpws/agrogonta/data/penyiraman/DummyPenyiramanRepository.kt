package com.ptpws.agrogonta.data.penyiraman

import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.models.PengadukModel
import com.ptpws.agrogontafarm.data.models.PenyiramanModel
import com.ptpws.agrogontafarm.data.models.SaluranModel
import com.ptpws.agrogontafarm.data.models.SuhuModel
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DummyPenyiramanRepository : PenyiramanRepository() {
    // Override StateFlow untuk memberikan data dummy
    override val penyiraman: StateFlow<PenyiramanModel> = MutableStateFlow(PenyiramanModel(0, 0))
    override val gh: StateFlow<GhModel> = MutableStateFlow(GhModel(PengadukModel(0, 0, 0, 0),
        SaluranModel(0), PenyiramanModel(0,0), SuhuModel(0)
    ))

    // Override semua fungsi untuk memberikan hasil dummy
    override suspend fun getPenyiraman() {
        // Gantilah implementasi Firebase dengan data dummy
        val dummyData = PenyiramanModel(0,0)
        _penyiraman.emit(dummyData)
    }


    override suspend fun setPenyiraman(data: String, nilai: Int): Resource<Unit> {
        // Dummy response untuk setPenyiraman
        return Resource.Success(Unit) // Gunakan Unit untuk menggantikan Void
    }


    override suspend fun getGh() {
        // Gantilah implementasi Firebase dengan data dummy
        val dummyData = GhModel(PengadukModel(0, 0, 0, 0),
            SaluranModel(0), PenyiramanModel(0,0), SuhuModel(0)
        )
        _gh.emit(dummyData)
    }

    override suspend fun setPengaduk(data: String, nilai: Int): Resource<Unit> {
        // Dummy response untuk setPengaduk
        return Resource.Success(Unit)
    }

    override suspend fun setSaluran(data: String, nilai: Int): Resource<Unit> {
        // Dummy response untuk setSaluran
        return Resource.Success(Unit)
    }
}
