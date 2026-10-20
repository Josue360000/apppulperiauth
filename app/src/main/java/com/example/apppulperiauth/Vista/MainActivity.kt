package com.example.apppulperiauth.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.apppulperiauth.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgUniversidad = findViewById<ImageView>(R.id.imgUniversidad)
        imgUniversidad.contentDescription = "Universidad"

        val btnProductos = findViewById<Button>(R.id.btnProductos)
        val btnMovimientos = findViewById<Button>(R.id.btnMovimientos)
        val btnResumen = findViewById<Button>(R.id.btnResumen)

        btnProductos.setOnClickListener {
            startActivity(Intent(this, ProductoActivity::class.java))
        }

        btnMovimientos.setOnClickListener {
            startActivity(Intent(this, MovimientoActivity::class.java))
        }

        btnResumen.setOnClickListener {
            startActivity(Intent(this, ResumenActivity::class.java))
        }
    }
}