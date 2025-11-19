package com.example.proyecto.Funciones

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object CarritoManager {
    private const val PREFS_NAME = "carrito_prefs"
    private const val KEY_CARRITO = "carrito_items"
    private var cache: MutableList<CarritoItem>? = null
    fun obtenerItems(context: Context): List<CarritoItem> {
        cache?.let { return it }

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString(KEY_CARRITO, null) ?: run {
            cache = mutableListOf()
            return cache!!
        }
        val lista = mutableListOf<CarritoItem>()
        val arr = JSONArray(jsonStr)
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            lista.add(
                CarritoItem(
                    nombre = obj.getString("nombre"),
                    tipo = obj.getString("tipo"),
                    precioClp = obj.getDouble("precioClp")
                )
            )
        }
        cache = lista
        return lista
    }
    fun agregarItem(context: Context, item: CarritoItem) {
        val lista = obtenerItems(context).toMutableList()
        lista.add(item)
        cache = lista
        guardarEnPrefs(context, lista)
    }
    fun limpiarCarrito(context: Context) {
        cache = mutableListOf()
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_CARRITO).apply()
    }
    private fun guardarEnPrefs(context: Context, lista: List<CarritoItem>) {
        val arr = JSONArray()
        for (item in lista) {
            val obj = JSONObject()
            obj.put("nombre", item.nombre)
            obj.put("tipo", item.tipo)
            obj.put("precioClp", item.precioClp)
            arr.put(obj)
        }
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_CARRITO, arr.toString()).apply()
    }
}
