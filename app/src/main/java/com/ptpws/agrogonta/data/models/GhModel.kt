package com.ptpws.agrogontafarm.data.models

data class GhModel(
    val pengadukModel: PengadukModel = PengadukModel(),
    val saluranmodel: SaluranModel = SaluranModel(),
    val penyiramanModel: PenyiramanModel = PenyiramanModel(),
    val suhu: SuhuModel = SuhuModel(),
    val kelembapan: KelembapanModel = KelembapanModel()
)
