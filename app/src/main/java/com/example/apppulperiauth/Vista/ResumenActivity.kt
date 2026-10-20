package com.example.apppulperiauth.Vista

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppulperiauth.Controlador.ResumenControlador
import com.example.apppulperiauth.R
import java.util.Locale

class ResumenActivity : AppCompatActivity() {

    private lateinit var tvValorTotal: TextView
    private lateinit var tvAlerta: TextView
    private lateinit var recyclerTop5: RecyclerView
    private lateinit var recyclerAlertas: RecyclerView
    private lateinit var adapterTop5: ProductoAdapter
    private lateinit var adapterAlertas: ProductoAdapter
    private lateinit var controlador: ResumenControlador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        controlador = ResumenControlador(this)

        tvValorTotal = findViewById(R.id.tvValorTotal)
        tvAlerta = findViewById(R.id.tvAlerta)
        recyclerTop5 = findViewById(R.id.recyclerTop5)
        recyclerAlertas = findViewById(R.id.recyclerAlertas)

        recyclerTop5.layoutManager = LinearLayoutManager(this)
        recyclerAlertas.layoutManager = LinearLayoutManager(this)

        adapterTop5 = ProductoAdapter(null, null)
        adapterAlertas = ProductoAdapter(null, null)
        recyclerTop5.adapter = adapterTop5
        recyclerAlertas.adapter = adapterAlertas

        cargarResumen()
    }

    private fun cargarResumen() {
        val r = controlador.obtenerResumen()
        val totalFormateado = String.format(Locale.getDefault(), "%.2f", r.valorTotal)
        tvValorTotal.text = "Valor total del inventario: L.$totalFormateado"
        adapterTop5.setLista(r.top5)
        adapterAlertas.setLista(r.alertas)
        if (r.alertas.isEmpty()) {
            tvAlerta.text = "Sin alertas de stock"
        } else {
            tvAlerta.text = "Alerta: ${r.alertas.size} producto(s) con stock minimo"
        }
    }
}