package com.example.apppulperiauth.BaseDatos

import android.content.ContentValues
import android.content.Context
import com.example.apppulperiauth.Modelo.Producto

class ProductoDAO(context: Context) {

    private val conexion = Conexion(context)

    fun insertar(p: Producto): Long {
        val db = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("name", p.name)
        cv.put("price", p.price)
        cv.put("stock", p.stock)
        cv.put("barcode", p.barcode)
        cv.put("min_stock", p.minStock)
        val id = db.insert(Conexion.TABLA_PRODUCTS, null, cv)
        db.close()
        return id
    }

    fun actualizar(p: Producto): Int {
        val db = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("name", p.name)
        cv.put("price", p.price)
        cv.put("stock", p.stock)
        cv.put("barcode", p.barcode)
        cv.put("min_stock", p.minStock)
        val r = db.update(Conexion.TABLA_PRODUCTS, cv, "id=?", arrayOf(p.id.toString()))
        db.close()
        return r
    }

    fun eliminar(id: Int): Int {
        val db = conexion.writableDatabase
        val r = db.delete(Conexion.TABLA_PRODUCTS, "id=?", arrayOf(id.toString()))
        db.close()
        return r
    }

    fun listar(): List<Producto> {
        val lista = mutableListOf<Producto>()
        val db = conexion.readableDatabase
        val c = db.rawQuery(
            "SELECT id, name, price, stock, barcode, min_stock FROM products ORDER BY name",
            null
        )
        while (c.moveToNext()) {
            lista.add(mapearProducto(c))
        }
        c.close()
        db.close()
        return lista
    }

    fun buscarPorId(id: Int): Producto? {
        val db = conexion.readableDatabase
        val c = db.rawQuery(
            "SELECT id, name, price, stock, barcode, min_stock FROM products WHERE id=?",
            arrayOf(id.toString())
        )
        val p = if (c.moveToFirst()) mapearProducto(c) else null
        c.close()
        db.close()
        return p
    }

    fun buscarPorBarcode(barcode: String): Producto? {
        val db = conexion.readableDatabase
        val c = db.rawQuery(
            "SELECT id, name, price, stock, barcode, min_stock FROM products WHERE barcode=?",
            arrayOf(barcode)
        )
        val p = if (c.moveToFirst()) mapearProducto(c) else null
        c.close()
        db.close()
        return p
    }

    fun actualizarStock(id: Int, nuevoStock: Int) {
        val db = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("stock", nuevoStock)
        db.update(Conexion.TABLA_PRODUCTS, cv, "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun valorTotalInventario(): Double {
        var total = 0.0
        val db = conexion.readableDatabase
        val c = db.rawQuery("SELECT SUM(price*stock) FROM products", null)
        if (c.moveToFirst()) {
            total = c.getDouble(0)
        }
        c.close()
        db.close()
        return total
    }

    fun top5PorValor(): List<Producto> {
        val lista = mutableListOf<Producto>()
        val db = conexion.readableDatabase
        val c = db.rawQuery(
            "SELECT id, name, price, stock, barcode, min_stock FROM products ORDER BY (price*stock) DESC LIMIT 5",
            null
        )
        while (c.moveToNext()) {
            lista.add(mapearProducto(c))
        }
        c.close()
        db.close()
        return lista
    }

    fun productosBajoStock(): List<Producto> {
        val lista = mutableListOf<Producto>()
        val db = conexion.readableDatabase
        val c = db.rawQuery(
            "SELECT id, name, price, stock, barcode, min_stock FROM products WHERE stock <= min_stock",
            null
        )
        while (c.moveToNext()) {
            lista.add(mapearProducto(c))
        }
        c.close()
        db.close()
        return lista
    }

    private fun mapearProducto(c: android.database.Cursor): Producto {
        return Producto(
            id = c.getInt(0),
            name = c.getString(1),
            price = c.getDouble(2),
            stock = c.getInt(3),
            barcode = c.getString(4),
            minStock = c.getInt(5)
        )
    }
}