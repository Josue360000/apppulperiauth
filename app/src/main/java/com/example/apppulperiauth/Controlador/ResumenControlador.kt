package com.example.apppulperiauth.Controlador

import android.content.Context
import com.example.apppulperiauth.BaseDatos.ProductoDAO
import com.example.apppulperiauth.Modelo.ResumenInventario

class ResumenControlador(context: Context) {

    private val productoDAO = ProductoDAO(context)

    fun obtenerResumen(): ResumenInventario {
        val total = productoDAO.valorTotalInventario()
        val top5 = productoDAO.top5PorValor()
        val alertas = productoDAO.productosBajoStock()
        return ResumenInventario(total, top5, alertas)
    }
}