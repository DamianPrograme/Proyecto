package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)

        val nombreJuego = intent.getStringExtra("nombreJuego") ?: "Juego"
        val precioJuego = intent.getStringExtra("precioJuego") ?: ""
        val btnVolver: Button = findViewById(R.id.btnVolverMenu)

        val txNombre = findViewById<TextView>(R.id.txNombreJuego)
        val txPrecio = findViewById<TextView>(R.id.txPrecioJuego)

        txNombre.text = nombreJuego
        txPrecio.text = "Precio: $precioJuego"

        btnVolver.setOnClickListener {
            val anteriorVentana = Intent(this, MainActivity3::class.java)
            startActivity(anteriorVentana)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}