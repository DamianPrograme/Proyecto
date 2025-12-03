package com.example.proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.Funciones.CarritoDao
import com.example.proyecto.Funciones.FormatoClp

class CarritoActivity : AppCompatActivity() {

    private lateinit var lvCarrito: ListView
    private lateinit var btnVaciar: Button
    private lateinit var btnVolver: Button

    private lateinit var carritoDao: CarritoDao
    private val formatoClp = FormatoClp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)

        carritoDao = CarritoDao(this)

        lvCarrito = findViewById(R.id.lvCarrito)
        btnVaciar = findViewById(R.id.btnVaciarCarrito)
        btnVolver = findViewById(R.id.btnVolverMenuCarrito)

        actualizarLista()

        btnVaciar.setOnClickListener {
            carritoDao.vaciar()
            actualizarLista()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun actualizarLista() {
        val items = carritoDao.obtenerTodos()
        val textos = items.map { item ->
            val precioStr = formatoClp.formatear(item.precioClp)
            "${item.nombre}\n${item.tipo}\n$precioStr CLP x${item.cantidad}"
        }
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, textos)
        lvCarrito.adapter = adaptador
    }
}

/*
Damian Ramos
Paolo Sala
Vicente Recabarren
 */
