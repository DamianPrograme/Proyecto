package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.API.LeerProductosApi
import com.example.proyecto.API.ProductoApi
import com.example.proyecto.Funciones.CombinarProductosWarhammer

class TiendaActivity : AppCompatActivity() {

    private lateinit var lvTienda: ListView
    private lateinit var btnCarrito: Button

    private val nombresProductos = mutableListOf<String>()
    private var productosApi: List<ProductoApi> = emptyList()
    private val combinadorWarhammer = CombinarProductosWarhammer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        lvTienda = findViewById(R.id.lvTienda)
        btnCarrito = findViewById(R.id.btnVerCarrito)

        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresProductos)
        lvTienda.adapter = adaptador

        lvTienda.setOnItemClickListener { _, _, position, _ ->
            if (position in productosApi.indices) {
                val producto = productosApi[position]
                val intent = Intent(this, DetalleJuegoActivity::class.java).apply {
                    putExtra("nombreJuego", producto.title)
                    putExtra("precioJuego", producto.price.toString())
                }
                startActivity(intent)
            }
        }

        btnCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }

        cargarProductos(adaptador)

        // Ajuste de padding según barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarProductos(adaptador: ArrayAdapter<String>) {
        val apiProductos = LeerProductosApi(this)
        apiProductos.cargarProductos(
            onResult = { lista ->
                productosApi = combinadorWarhammer.combinar(lista)
                nombresProductos.clear()
                nombresProductos.addAll(productosApi.map { it.title })
                adaptador.notifyDataSetChanged()
            },
            onError = { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                if (nombresProductos.isEmpty()) {
                    val mock = listOf(
                        ProductoApi(1, "Dummy 1", 7.9),
                        ProductoApi(2, "Dummy 2", 15.8),
                        ProductoApi(3, "Dummy 3", 22.9)
                    )
                    productosApi = combinadorWarhammer.combinar(mock)
                    nombresProductos.clear()
                    nombresProductos.addAll(productosApi.map { it.title })
                    adaptador.notifyDataSetChanged()
                }
            }
        )
    }

    // Ahora el botón de retroceso del dispositivo regresa al menú principal
    override fun onBackPressed() {
        super.onBackPressed()
    }
}


/*
Damian Ramos
Paolo Sala
Vicente Recabarren
 */
