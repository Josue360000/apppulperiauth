package com.example.apppulperiauth.Modelo

data class Producto(
    var id: Int = 0,
    var name: String = "",
    var price: Double = 0.0,
    var stock: Int = 0,
    var barcode: String? = null,
    var minStock: Int = 5
) {
    fun getValorTotal(): Double {
        return price * stock
    }

    fun bajoStock(): Boolean {
        return stock <= minStock
    }
}