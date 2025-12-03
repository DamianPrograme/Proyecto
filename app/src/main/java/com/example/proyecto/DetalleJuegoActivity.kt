package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.API.LeerMonedaApi
import com.example.proyecto.Funciones.CarritoDao
import com.example.proyecto.Funciones.CarritoItem
import com.example.proyecto.Funciones.FormatoClp
import com.example.proyecto.Funciones.ObtenerTipoJuego

class DetalleJuegoActivity : AppCompatActivity() {

    private lateinit var txNombre: TextView
    private lateinit var txTipo: TextView
    private lateinit var txPrecio: TextView
    private lateinit var btnVolver: Button
    private lateinit var btnAgregarCarrito: Button

    private val obtenerTipoJuego = ObtenerTipoJuego()
    private val formatoClp = FormatoClp()
    private var precioClpActual: Double? = null
    private lateinit var carritoDao: CarritoDao

    companion object {
        private const val PRECIO_MAX_CLP = 35_000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)

        carritoDao = CarritoDao(this)

        txNombre = findViewById(R.id.txNombreJuego)
        txTipo   = findViewById(R.id.txTipoJuego)
        txPrecio = findViewById(R.id.txPrecioJuego)
        btnVolver = findViewById(R.id.btnVolverMenu)
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito)

        val nombreJuego = intent.getStringExtra("nombreJuego") ?: "Juego"
        val precioUsdStr = intent.getStringExtra("precioJuego") ?: "0.0"
        val precioUsd = precioUsdStr.toDoubleOrNull() ?: 0.0

        txNombre.text = nombreJuego
        txTipo.text = "Tipo: ${obtenerTipoJuego.obtener(nombreJuego)}"
        txPrecio.text = "Precio: US$ %.2f (convirtiendo a CLP...)".format(precioUsd)

        val apiMoneda = LeerMonedaApi(this)
        apiMoneda.obtenerFactorUsdAClp(
            onResult = { factorUsdAClp ->
                val precioClpBruto = precioUsd * factorUsdAClp
                val precioClpEntero = precioClpBruto.toInt()
                val precioClpTope = if (precioClpEntero > PRECIO_MAX_CLP) {
                    PRECIO_MAX_CLP
                } else {
                    precioClpEntero
                }
                precioClpActual = precioClpTope.toDouble()
                val precioFormateado = formatoClp.formatear(precioClpTope.toDouble())
                txPrecio.text = "Precio: $precioFormateado CLP"
            },
            onError = { msg ->
                txPrecio.text = "Precio: US$ %.2f (sin conversión a CLP)".format(precioUsd)
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        )

        btnAgregarCarrito.setOnClickListener {
            val precioClp = precioClpActual
            if (precioClp == null) {
                Toast.makeText(this, "Espera a que se calcule el precio en CLP", Toast.LENGTH_SHORT).show()
            } else {
                val item = CarritoItem(
                    nombre = nombreJuego,
                    tipo = obtenerTipoJuego.obtener(nombreJuego),
                    precioClp = precioClp,
                    cantidad = 1
                )

                val idInsertado = carritoDao.insertar(item)

                if (idInsertado != -1L) {
                    Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al guardar en el carrito", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnVolver.setOnClickListener {
            val anteriorVentana = Intent(this, TiendaActivity::class.java)
            startActivity(anteriorVentana)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
