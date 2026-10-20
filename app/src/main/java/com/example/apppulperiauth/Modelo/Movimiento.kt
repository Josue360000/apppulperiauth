package com.example.apppulperiauth.Modelo

data class Movimiento(
    var id: Int = 0,
    var productId: Int = 0,
    var qty: Int = 0,
    var type: String = "",
    var date: String = ""
)