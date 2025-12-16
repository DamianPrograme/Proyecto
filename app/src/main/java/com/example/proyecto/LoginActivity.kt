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

class LoginActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPasswd: EditText
    private lateinit var btnIngreso: Button
    private lateinit var txMensaje: TextView

    private val defaultUsername = "admin"
    private val defaultPassword = "admin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bindViews()
        setupFieldListeners()
        setupLoginButton()
        handleSystemInsets()
    }

    private fun bindViews() {
        edUsername = findViewById(R.id.edUsername)
        edPasswd = findViewById(R.id.edPasswd)
        btnIngreso = findViewById(R.id.btnIngresar)
        txMensaje = findViewById(R.id.txMensaje)
    }

    private fun setupFieldListeners() {
        btnIngreso.isEnabled = false
        val validator = { validateFields() }
        edUsername.addTextChangedListener { validator() }
        edPasswd.addTextChangedListener { validator() }
    }

    private fun validateFields() {
        val username = edUsername.text.toString().trim()
        val password = edPasswd.text.toString().trim()
        btnIngreso.isEnabled = username.isNotEmpty() && password.isNotEmpty()
    }

    private fun setupLoginButton() {
        btnIngreso.setOnClickListener {
            btnIngreso.isEnabled = false // Evita doble click
            val username = edUsername.text.toString()
            val password = edPasswd.text.toString()
            if (username == defaultUsername && password == defaultPassword) {
                navigateToMenu(username)
            } else {
                showErrorMessage("Error: Usuario o contraseña incorrectos")
                btnIngreso.isEnabled = true
            }
        }
    }

    private fun navigateToMenu(username: String) {
        val intent = Intent(this, MenuPrincipalActivity::class.java)
        intent.putExtra("par_usern", username)
        startActivity(intent)
        finish()
    }

    private fun showErrorMessage(message: String) {
        txMensaje.text = message
    }

    private fun handleSystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


/*
Damian Ramos
Paolo Sala
Vicente Recabarren
 */