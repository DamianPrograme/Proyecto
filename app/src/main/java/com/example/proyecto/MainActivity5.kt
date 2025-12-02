package com.example.proyecto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.media.ExifInterface
import com.example.proyecto.PCamara.CameraManager
import com.example.proyecto.PCamara.CamaraUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity5 : AppCompatActivity() {

    private var cameraManager: CameraManager? = null
    private lateinit var previewView: PreviewView
    private lateinit var contenedorFoto: ImageView

    private lateinit var btnfoto: Button
    private lateinit var btnCargarFoto: Button
    private lateinit var btnCapturar: Button
    private lateinit var btnregresar: Button

    private val REQUEST_GALLERY = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5)

        btnfoto = findViewById(R.id.btnTomar)
        btnCargarFoto = findViewById(R.id.btnCargar)
        btnCapturar = findViewById(R.id.btnCapturar)
        contenedorFoto = findViewById(R.id.imgvFoto)
        previewView = findViewById(R.id.previewView)
        btnregresar = findViewById(R.id.btnRegresar)

        btnfoto.isEnabled = false
        btnCapturar.visibility = View.GONE

        // Permiso
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            setupCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        }

        // TOMAR FOTO
        btnfoto.setOnClickListener {
            cameraManager?.takePhoto { bitmap ->
                if (bitmap != null) {

                    val corregida = rotarBitmap90(bitmap)

                    mostrarBitmap(corregida)
                    guardarEnMemoriaInterna(corregida)

                } else {
                    Toast.makeText(this, "Error al capturar", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // CARGAR FOTO
        btnCargarFoto.setOnClickListener {
            abrirGaleria()
        }

        // VOLVER A CAPTURAR
        btnCapturar.setOnClickListener {
            contenedorFoto.visibility = View.GONE
            previewView.visibility = View.VISIBLE
            btnCapturar.visibility = View.GONE
            cameraManager?.startCamera(previewView)
        }

        // REGRESAR
        btnregresar.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sb = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom)
            insets
        }
    }

    // PERMISO
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101 && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            setupCamera()
        }
    }

    private fun setupCamera() {
        cameraManager = CameraManager(this)
        cameraManager?.startCamera(previewView)
        btnfoto.isEnabled = true
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    //IMÁGENES DE GALERÍA
    private fun corregirOrientacionGaleria(uri: Uri, bitmap: Bitmap): Bitmap {
        return try {
            val input: InputStream? = contentResolver.openInputStream(uri)
            val exif = ExifInterface(input!!)

            val orientacion = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = Matrix()

            when (orientacion) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }

            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        } catch (e: Exception) {
            bitmap
        }
    }

    //ROTAR FOTO DE CÁMARA (CameraX la entrega horizontal)
    private fun rotarBitmap90(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    // GALERÍA
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            val uri = data?.data ?: return
            try {
                var bitmap = CamaraUtils.uriToBitmap(this, uri)

                bitmap = corregirOrientacionGaleria(uri, bitmap)   // ⭐ AGREGADO

                mostrarBitmap(bitmap)
                guardarEnMemoriaInterna(bitmap)

            } catch (e: Exception) {
                Toast.makeText(this, "Error al cargar imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // MOSTRAR FOTO
    private fun mostrarBitmap(bitmap: Bitmap) {
        contenedorFoto.setImageBitmap(bitmap)

        previewView.visibility = View.GONE
        contenedorFoto.visibility = View.VISIBLE
        btnCapturar.visibility = View.VISIBLE

        val base64 = CamaraUtils.convertirDeBitMapABase64(bitmap)
        Log.d("BASE64", base64.take(80) + "...")
    }

    // GUARDAR INTERNO
    private fun guardarEnMemoriaInterna(bitmap: Bitmap) {
        try {
            val archivo = File(filesDir, "foto_capturada.jpg")
            val fos = FileOutputStream(archivo)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            fos.close()

            Toast.makeText(this, "Imagen guardada", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
        }
    }
}
