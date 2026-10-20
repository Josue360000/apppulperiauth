package com.example.apppulperiauth.BaseDatos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.apppulperiauth.Modelo.Movimiento

class MovimientoDAO(context: Context) {

    private val conexion = Conexion(context)

    fun insertar(m: Movimiento): Long {
        val db = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("product_id", m.productId)
        cv.put("qty", m.qty)
        cv.put("type", m.type)
        cv.put("date", m.date)
        val id = db.insert(Conexion.TABLA_MOVEMENTS, null, cv)
        db.close()
        return id
    }

    fun listar(): List<Movimiento> {
        val lista = mutableListOf<Movimiento>()
        val db = conexion.readableDatabase
        val c = db.rawQuery(
            "SELECT id, product_id, qty, type, date FROM movements ORDER BY id DESC",
            null
        )
        while (c.moveToNext()) {
            lista.add(mapearMovimiento(c))
        }
        c.close()
        db.close()
        return lista
    }

    private fun mapearMovimiento(c: Cursor): Movimiento {
        return Movimiento(
            id = c.getInt(0),
            productId = c.getInt(1),
            qty = c.getInt(2),
            type = c.getString(3),
            date = c.getString(4)
        )
    }
}