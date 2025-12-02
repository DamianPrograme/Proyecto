package com.example.proyecto.PCamara

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object CamaraUtils {

    // Convertir Bitmap --> Base64
    fun convertirDeBitMapABase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        val byteArray = outputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    // Convertir URI --> Bitmap (para imágenes desde la galería)
    fun uriToBitmap(context: Context, uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }
}
