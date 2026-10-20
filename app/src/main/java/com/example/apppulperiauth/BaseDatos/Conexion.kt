package com.example.apppulperiauth.BaseDatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Conexion(context: Context) : SQLiteOpenHelper(context, NOMBRE_BD, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "price REAL NOT NULL, " +
                    "stock INTEGER NOT NULL, " +
                    "barcode TEXT, " +
                    "min_stock INTEGER NOT NULL DEFAULT 5)"
        )
        db.execSQL(
            "CREATE TABLE movements (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "product_id INTEGER NOT NULL, " +
                    "qty INTEGER NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "FOREIGN KEY(product_id) REFERENCES products(id))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS movements")
        db.execSQL("DROP TABLE IF EXISTS products")
        onCreate(db)
    }

    companion object {
        private const val NOMBRE_BD = "tienda.db"
        private const val VERSION = 1
        const val TABLA_PRODUCTS = "products"
        const val TABLA_MOVEMENTS = "movements"
    }
}