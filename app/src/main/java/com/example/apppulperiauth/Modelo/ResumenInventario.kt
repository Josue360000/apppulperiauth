package com.example.apppulperiauth.Modelo

data class ResumenInventario(
    var valorTotal: Double = 0.0,
    var top5: List<Producto> = emptyList(),
    var alertas: List<Producto> = emptyList()
)