package com.example.apppulperiauth.Controlador

import android.content.Context
import com.example.apppulperiauth.BaseDatos.MovimientoDAO
import com.example.apppulperiauth.BaseDatos.ProductoDAO
import com.example.apppulperiauth.Modelo.Movimiento
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovimientoControlador(context: Context) {

    private val movimientoDAO = MovimientoDAO(context)
    private val productoDAO = ProductoDAO(context)

    fun registrarMovimiento(productId: Int, qty: Int, type: String): String {
        val p = productoDAO.buscarPorId(productId) ?: return "Producto no encontrado"

        val nuevoStock: Int
        if (type == "ENTRADA") {
            nuevoStock = p.stock + qty
        } else if (type == "SALIDA") {
            if (qty > p.stock) {
                return "Stock insuficiente para realizar la salida"
            }
            nuevoStock = p.stock - qty
        } else {
            return "Tipo de movimiento invalido"
        }

        val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val m = Movimiento(0, productId, qty, type, fecha)
        movimientoDAO.insertar(m)
        productoDAO.actualizarStock(productId, nuevoStock)

        return if (nuevoStock <= p.minStock) {
            "Movimiento registrado. Alerta: stock minimo alcanzado para ${p.name}"
        } else {
            "Movimiento registrado correctamente"
        }
    }

    fun listarMovimientos(): List<Movimiento> {
        return movimientoDAO.listar()
    }
}