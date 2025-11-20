package com.example.proyecto.API

import android.app.Activity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

// Api de conversión de moneda a CLP

class LeerMonedaApi(private val activity: Activity) {
    fun obtenerFactorUsdAClp(
        onResult: (Double) -> Unit,
        onError: (String) -> Unit
    ) {
        thread {
            try {
                val url = URL("https://api.exchangerate-api.com/v4/latest/USD")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val jsonText = conn.inputStream.bufferedReader().use { it.readText() }

                    val root = JSONObject(jsonText)
                    val rates = root.getJSONObject("rates")
                    val clpRate = rates.getDouble("CLP") // CLP por 1 USD

                    activity.runOnUiThread {
                        onResult(clpRate)
                    }
                } else {
                    activity.runOnUiThread {
                        onError("Error HTTP moneda: $responseCode")
                    }
                }

                conn.disconnect()
            } catch (e: Exception) {
                activity.runOnUiThread {
                    onError(e.message ?: "Error desconocido en moneda")
                }
            }
        }
    }
}
