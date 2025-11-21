package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edUsername: EditText = findViewById(R.id.edUsername)
        val edPasswd: EditText = findViewById(R.id.edPasswd)
        val btnIngreso: Button = findViewById(R.id.btnIngresar)
        val txMensaje: TextView = findViewById(R.id.txMensaje)

        var defUsername = "admin"
        var defPasswd = "admin"

        btnIngreso.isEnabled = false
        fun validarcampos(){
            val usuario = edUsername.text.toString().trim()
            val pass = edPasswd.text.toString().trim()
            btnIngreso.isEnabled = usuario.isNotEmpty() && pass.isNotEmpty()
        }

        edUsername.addTextChangedListener { validarcampos() }
        edPasswd.addTextChangedListener { validarcampos() }

        btnIngreso.setOnClickListener {
            if(edUsername.text.toString() == defUsername.toString()
                && edPasswd.text.toString() == defPasswd.toString()){
                val nuevaVentana = Intent(this, MainActivity2::class.java)
                nuevaVentana.putExtra("par_usern",edUsername.text.toString())
                startActivity(nuevaVentana)
            }else{
                txMensaje.text = "Error Usuario/Contraseña"
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
