package com.example.proyecto

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class CamaraActivity : AppCompatActivity() {

    private lateinit var btnTomarFoto: Button
    private lateinit var btnCargarFoto: Button
    private lateinit var imgvFoto: ImageView
    private lateinit var previewView: PreviewView

    private var imageCapture: ImageCapture? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var showingImage = false

    /* Permiso de cámara */
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) startCamera()
            else Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        previewView = findViewById(R.id.previewView)
        btnTomarFoto = findViewById(R.id.btnTomar)
        btnCargarFoto = findViewById(R.id.btnCargar)
        imgvFoto = findViewById(R.id.imgvFoto)

        checkCameraPermission()

        btnTomarFoto.setOnClickListener {
            if (!showingImage) takePhoto()
        }

        btnCargarFoto.setOnClickListener {
            if (!showingImage) loadPhotoFromGallery()
        }
    }

    /*
       PERMISOS
    */
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            startCamera()
        }
    }

    /*
       INICIAR CÁMARA
    */
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetRotation(previewView.display.rotation) // 🔧 CORREGIDO
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                imgvFoto.visibility = ImageView.GONE
                previewView.visibility = PreviewView.VISIBLE
                showingImage = false

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al iniciar la cámara", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    /*
       TOMAR FOTO
    */
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_$name.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ProyectoFotos")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@CamaraActivity,
                        "Error al guardar la foto",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    outputFileResults.savedUri?.let { uri ->
                        mostrarImagen(uri)
                        Toast.makeText(
                            this@CamaraActivity,
                            "Foto guardada en galería",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }

    /*
       GALERÍA
    */
    private fun loadPhotoFromGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                mostrarImagen(uri)
                Toast.makeText(this, "Foto cargada de galería", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
       MOSTRAR IMAGEN
    */
    private fun mostrarImagen(uri: Uri) {
        imgvFoto.setImageURI(uri)
        imgvFoto.visibility = ImageView.VISIBLE
        previewView.visibility = PreviewView.INVISIBLE
        showingImage = true
    }

    /*
       BOTÓN ATRÁS
    */
    override fun onBackPressed() {
        if (showingImage) {
            startCamera() // vuelve a cámara
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
    }
}
