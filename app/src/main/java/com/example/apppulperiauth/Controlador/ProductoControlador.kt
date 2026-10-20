package com.example.apppulperiauth.Controlador

import android.content.Context
import com.example.apppulperiauth.BaseDatos.ProductoDAO
import com.example.apppulperiauth.Modelo.Producto

class ProductoControlador(context: Context) {

    private val dao = ProductoDAO(context)

    fun crearProducto(
        name: String,
        price: Double,
        stock: Int,
        barcode: String,
        minStock: Int
    ): Long {
        val p = Producto(0, name, price, stock, barcode, minStock)
        return dao.insertar(p)
    }

    fun actualizarProducto(
        id: Int,
        name: String,
        price: Double,
        stock: Int,
        barcode: String,
        minStock: Int
    ): Int {
        val p = Producto(id, name, price, stock, barcode, minStock)
        return dao.actualizar(p)
    }

    fun eliminarProducto(id: Int): Int {
        return dao.eliminar(id)
    }

    fun listarProductos(): List<Producto> {
        return dao.listar()
    }

    fun buscarProducto(id: Int): Producto? {
        return dao.buscarPorId(id)
    }

    fun buscarPorBarcode(barcode: String): Producto? {
        return dao.buscarPorBarcode(barcode)
    }
}