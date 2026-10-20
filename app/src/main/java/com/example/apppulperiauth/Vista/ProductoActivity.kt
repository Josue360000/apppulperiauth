package com.example.apppulperiauth.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppulperiauth.Controlador.ProductoControlador
import com.example.apppulperiauth.Modelo.Producto
import com.example.apppulperiauth.R

class ProductoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etStock: EditText
    private lateinit var etBarcode: EditText
    private lateinit var etMinStock: EditText
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var controlador: ProductoControlador
    private var idEditando: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        controlador = ProductoControlador(this)

        etNombre = findViewById(R.id.etNombre)
        etPrecio = findViewById(R.id.etPrecio)
        etStock = findViewById(R.id.etStock)
        etBarcode = findViewById(R.id.etBarcode)
        etMinStock = findViewById(R.id.etMinStock)
        recycler = findViewById(R.id.recyclerProductos)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProducto)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ProductoAdapter(
            onEditar = { p -> seleccionarParaEditar(p) },
            onEliminar = { p -> confirmarEliminar(p) }
        )
        recycler.adapter = adapter

        btnGuardar.setOnClickListener { guardarProducto() }

        cargarLista()
    }

    private fun guardarProducto() {
        val nombre = etNombre.text.toString().trim()
        val precioStr = etPrecio.text.toString().trim()
        val stockStr = etStock.text.toString().trim()
        val barcode = etBarcode.text.toString().trim()
        val minStr = etMinStock.text.toString().trim()

        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || minStr.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val precio: Double
        val stock: Int
        val minStock: Int
        try {
            precio = precioStr.toDouble()
            stock = stockStr.toInt()
            minStock = minStr.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Valores numericos invalidos", Toast.LENGTH_SHORT).show()
            return
        }

        if (idEditando == -1) {
            val r = controlador.crearProducto(nombre, precio, stock, barcode, minStock)
            if (r > 0) {
                Toast.makeText(this, "Producto creado", Toast.LENGTH_SHORT).show()
                limpiar()
                cargarLista()
            } else {
                Toast.makeText(this, "Error al crear", Toast.LENGTH_SHORT).show()
            }
        } else {
            val r = controlador.actualizarProducto(idEditando, nombre, precio, stock, barcode, minStock)
            if (r > 0) {
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
                limpiar()
                cargarLista()
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun seleccionarParaEditar(p: Producto) {
        idEditando = p.id
        etNombre.setText(p.name)
        etPrecio.setText(p.price.toString())
        etStock.setText(p.stock.toString())
        etBarcode.setText(p.barcode)
        etMinStock.setText(p.minStock.toString())
        Toast.makeText(this, "Editando producto", Toast.LENGTH_SHORT).show()
    }

    private fun confirmarEliminar(p: Producto) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar producto")
            .setMessage("Desea eliminar ${p.name}?")
            .setPositiveButton("Si") { _, _ ->
                controlador.eliminarProducto(p.id)
                cargarLista()
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun limpiar() {
        idEditando = -1
        etNombre.setText("")
        etPrecio.setText("")
        etStock.setText("")
        etBarcode.setText("")
        etMinStock.setText("")
    }

    private fun cargarLista() {
        val lista = controlador.listarProductos()
        adapter.setLista(lista)
    }
}