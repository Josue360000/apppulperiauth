package com.example.apppulperiauth.Vista

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppulperiauth.Controlador.MovimientoControlador
import com.example.apppulperiauth.Controlador.ProductoControlador
import com.example.apppulperiauth.Modelo.Producto
import com.example.apppulperiauth.R

class MovimientoActivity : AppCompatActivity() {

    private lateinit var spProducto: Spinner
    private lateinit var spTipo: Spinner
    private lateinit var etCantidad: EditText
    private lateinit var movimientoControlador: MovimientoControlador
    private lateinit var productoControlador: ProductoControlador
    private var productos: List<Producto> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimiento)

        movimientoControlador = MovimientoControlador(this)
        productoControlador = ProductoControlador(this)

        spProducto = findViewById(R.id.spProducto)
        spTipo = findViewById(R.id.spTipo)
        etCantidad = findViewById(R.id.etCantidad)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarMovimiento)

        cargarProductos()

        val adapterTipo = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("ENTRADA", "SALIDA")
        )
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipo.adapter = adapterTipo

        btnRegistrar.setOnClickListener { registrar() }
    }

    private fun cargarProductos() {
        productos = productoControlador.listarProductos()
        val nombres = productos.map { p ->
            "${p.id} - ${p.name} (stock ${p.stock})"
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombres
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProducto.adapter = adapter
    }

    private fun registrar() {
        if (productos.isEmpty()) {
            Toast.makeText(this, "No hay productos registrados", Toast.LENGTH_SHORT).show()
            return
        }
        val pos = spProducto.selectedItemPosition
        val p = productos[pos]
        val cantStr = etCantidad.text.toString().trim()
        if (cantStr.isEmpty()) {
            Toast.makeText(this, "Ingrese la cantidad", Toast.LENGTH_SHORT).show()
            return
        }
        val qty: Int
        try {
            qty = cantStr.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Cantidad invalida", Toast.LENGTH_SHORT).show()
            return
        }
        if (qty <= 0) {
            Toast.makeText(this, "La cantidad debe ser mayor a cero", Toast.LENGTH_SHORT).show()
            return
        }
        val tipo = spTipo.selectedItem.toString()
        val mensaje = movimientoControlador.registrarMovimiento(p.id, qty, tipo)
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        etCantidad.setText("")
        cargarProductos()
    }
}