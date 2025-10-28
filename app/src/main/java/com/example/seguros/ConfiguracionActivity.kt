package com.example.seguros

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ConfiguracionActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE)

        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        etTelefono.setText(prefs.getString("telefono", ""))

        btnGuardar.setOnClickListener {
            val telefono = etTelefono.text.toString()

            if (telefono.isNotEmpty()) {
                prefs.edit().putString("telefono", telefono).apply()
                Toast.makeText(this, "Teléfono guardado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Introduce un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}