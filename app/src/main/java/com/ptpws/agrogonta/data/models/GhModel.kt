package com.ptpws.agrogontafarm.data.models

data class GhModel(
    val pengadukModel: PengadukModel = PengadukModel(),
    val saluranmodel: SaluranModel = SaluranModel(),
    val penyiramanModel: PenyiramanModel = PenyiramanModel(),
    val suhuModel: SuhuModel = SuhuModel()
)
