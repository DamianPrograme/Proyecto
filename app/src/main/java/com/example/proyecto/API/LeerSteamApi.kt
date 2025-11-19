package com.example.proyecto.API

import android.app.Activity
import com.example.proyecto.API.JuegoSteam
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LeerSteamApi(private val activity: Activity) {

    fun cargarJuegosWarhammer(
        onResult: (List<JuegoSteam>) -> Unit,
        onError: (String) -> Unit
    ) {
        thread {
            try {
                val url = URL("https://api.steampowered.com/ISteamApps/GetAppList/v2/")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val jsonText = conn.inputStream.bufferedReader().use { it.readText() }

                    val root = JSONObject(jsonText)
                    val appsArray = root
                        .getJSONObject("applist")
                        .getJSONArray("apps")

                    val lista = mutableListOf<JuegoSteam>()

                    for (i in 0 until appsArray.length()) {
                        val appObj = appsArray.getJSONObject(i)
                        val name = appObj.getString("name")
                        val appid = appObj.getInt("appid")

                        if (name.contains("warhammer", ignoreCase = true)) {
                            lista.add(JuegoSteam(appid, name))
                        }
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
