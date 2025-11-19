package com.example.proyecto.API

import android.app.Activity
import com.example.proyecto.API.ProductoApi
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LeerProductosApi(private val activity: Activity) {

    fun cargarProductos(
        onResult: (List<ProductoApi>) -> Unit,
        onError: (String) -> Unit
    ) {
        thread {
            try {
                val url = URL("https://dummyjson.com/products")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val jsonText = conn.inputStream.bufferedReader().use { it.readText() }

                    val root = JSONObject(jsonText)
                    val productsArray = root.getJSONArray("products")

                    val lista = mutableListOf<ProductoApi>()

                    for (i in 0 until productsArray.length()) {
                        val prodObj = productsArray.getJSONObject(i)
                        val id = prodObj.getInt("id")
                        val title = prodObj.getString("title")
                        val price = prodObj.getDouble("price")

                        lista.add(ProductoApi(id, title, price))
                    }

                    activity.runOnUiThread {
                        onResult(lista)
                    }
                } else {
                    activity.runOnUiThread {
                        onError("Error HTTP: $responseCode")
                    }
                }

                conn.disconnect()
            } catch (e: Exception) {
                activity.runOnUiThread {
                    onError(e.message ?: "Error desconocido")
                }
            }
        }
    }
}
