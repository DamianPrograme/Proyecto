package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        val LvTienda: ListView = findViewById(R.id.lvTienda)
        val btnVolver: Button = findViewById(R.id.btnVolverMenu)

        val opcionesArr = arrayOf(
            "Warhammer 40,000: Dawn of War - Anniversary Edition",
            "Warhammer 40,000: Dawn of War - Definitive Edition",
            "Warhammer 40,000: Dawn of War II - Anniversary Edition",
            "Warhammer 40,000: Dawn of War III",
            "Warhammer 40,000: Dawn of War IV"
        )

        val preciosArr = arrayOf(
            "$7.900",
            "$15.800",
            "$22.900",
            "$35.000",
            "PROXIMAMENTE"
        )

        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, opcionesArr
        )

        LvTienda.adapter = adaptador

        LvTienda.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("nombreJuego", opcionesArr[position])
            intent.putExtra("precioJuego", preciosArr[position])
            startActivity(intent)
        }

        btnVolver.setOnClickListener {
            val anteriorVentana = Intent(this, MainActivity2::class.java)
            startActivity(anteriorVentana)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}