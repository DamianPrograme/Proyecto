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

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var lvOpciones: ListView
    private lateinit var txUsuario: TextView
    private val opcionesArr = arrayOf("Tienda", "Producto", "Camara")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        bindViews()
        setUsuario()
        setupListView()
        handleSystemInsets()
    }

    private fun bindViews() {
        lvOpciones = findViewById(R.id.LvOpciones)
        txUsuario = findViewById(R.id.txUsuario)
    }

    private fun setUsuario() {
        val recibirUsername = intent.getStringExtra("par_usern")
        txUsuario.text = recibirUsername ?: "Usuario"
    }

    private fun setupListView() {
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcionesArr)
        lvOpciones.adapter = adaptador

        lvOpciones.setOnItemClickListener { parent, _, position, _ ->
            val itemElegido = parent.getItemAtPosition(position).toString()
            navigateTo(itemElegido)
        }
    }

    private fun navigateTo(opcion: String) {
        val intent = when(opcion) {
            "Tienda" -> Intent(this, TiendaActivity::class.java)
            "Producto" -> Intent(this, DetalleJuegoActivity::class.java)
            "Camara" -> Intent(this, CamaraActivity::class.java)
            else -> null
        }

        intent?.let {
            startActivity(it)
        }
    }

    private fun handleSystemInsets() {
        val mainView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


/*
Damian Ramos
Paolo Sala
Vicente Recabarren
 */