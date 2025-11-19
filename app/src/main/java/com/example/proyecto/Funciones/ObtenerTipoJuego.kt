package com.example.proyecto.Funciones

class ObtenerTipoJuego {

    fun obtener(nombre: String): String = when {
        nombre.contains("Dawn of War", ignoreCase = true) ->
            "Estrategia en tiempo real (RTS)"
        nombre.contains("Gladius", ignoreCase = true) ->
            "4X / Estrategia por turnos"
        nombre.contains("Mechanicus", ignoreCase = true) ->
            "Estrategia táctica por turnos"
        nombre.contains("Space Marine 2", ignoreCase = true) ||
                nombre.contains("Space Marine", ignoreCase = true) ->
            "Acción en tercera persona"
        nombre.contains("Boltgun", ignoreCase = true) ||
                nombre.contains("Darktide", ignoreCase = true) ->
            "Acción / Shooter"
        nombre.contains("Chaos Gate", ignoreCase = true) ||
                nombre.contains("Sanctus Reach", ignoreCase = true) ->
            "Estrategia táctica"
        nombre.contains("Battlefleet Gothic", ignoreCase = true) ->
            "Estrategia espacial"
        else ->
            "Acción / Estrategia"
    }
}
