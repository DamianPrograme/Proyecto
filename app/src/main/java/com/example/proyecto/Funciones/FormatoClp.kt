package com.example.proyecto.Funciones

import java.text.NumberFormat
import java.util.Locale

class FormatoClp {
    private val nf: NumberFormat = NumberFormat.getInstance(Locale("es", "CL")).apply {
        maximumFractionDigits = 0
    }
    fun formatear(montoClp: Double): String = nf.format(montoClp)
}
