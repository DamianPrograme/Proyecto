package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val LvOpciones: ListView = findViewById(R.id.LvOpciones)
        val txUsuario: TextView = findViewById(R.id.txUsuario)
        val recibirUsername = intent.getStringExtra("par_usern")

        txUsuario.text = recibirUsername.toString()

        val opcionesArr = arrayOf(
            "Tienda",
            "Producto"
        )

        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, opcionesArr
        )

        LvOpciones.adapter = adaptador

        LvOpciones.setOnItemClickListener { parent, view, position, id ->
            val itemElegido = parent.getItemAtPosition(position).toString()

            if (itemElegido == "Tienda") {
                val abrirTienda = Intent(this, MainActivity3::class.java)
                startActivity(abrirTienda)
            } else if (itemElegido == "Producto") {
                val abrirProducto = Intent(this, MainActivity4::class.java)
                startActivity(abrirProducto)

            }




            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}

/*
Damian Ramos
Paolo Sala
Vicente Recabarren
 */