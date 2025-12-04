package com.example.seguros

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*

class ConfiguracionActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var layoutPrincipal: LinearLayout
    private lateinit var progresoCircular: ProgressBar
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)
        val btnFechaAsistencia = findViewById<Button>(R.id.btnConfirmarAsistencia)
        val cbRenovacion = findViewById<CheckBox>(R.id.cbRenovacion)
        val cbFacturas = findViewById<CheckBox>(R.id.cbFacturas)
        val cbComercial = findViewById<CheckBox>(R.id.cbComercial)
        val rgAsistencia = findViewById<RadioGroup>(R.id.rgAsistencia)
        val swUbicacion = findViewById<Switch>(R.id.swUbicacion)

        btnFechaAsistencia.setOnClickListener {
            val intent = Intent(this, FechaAsistenciaActivity::class.java)
            startActivity(intent)
        }
        cbRenovacion.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Avisos de renovación activados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Avisos de renovación desactivados", Toast.LENGTH_SHORT).show()
            }
        }
        cbFacturas.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Avisos de facturas activados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Avisos de facturas desactivados", Toast.LENGTH_SHORT).show()
            }
        }
        cbComercial.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Info comercial activada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Info comercial desactivada", Toast.LENGTH_SHORT).show()
            }
        }
        rgAsistencia.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbCarretera) {
                Toast.makeText(this, "Seleccionado: Asistencia en carretera", Toast.LENGTH_SHORT).show()
            } else if (checkedId == R.id.rbHogar) {
                Toast.makeText(this, "Seleccionado: Asistencia en hogar", Toast.LENGTH_SHORT).show()
            } else if (checkedId == R.id.rbMedica) {
                Toast.makeText(this, "Seleccionado: Asistencia médica", Toast.LENGTH_SHORT).show()
            }
        }
        swUbicacion.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Se enviará la ubicación automáticamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Desactivada ubicación automática", Toast.LENGTH_SHORT).show()
            }
        }
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE)

        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        btnGuardar = findViewById(R.id.btnGuardar)
        layoutPrincipal = findViewById(R.id.layoutPrincipal)
        progresoCircular = findViewById(R.id.progresoCircular)

        etTelefono.setText(prefs.getString("telefono", ""))

        btnGuardar.setOnClickListener {
            val telefono = etTelefono.text.toString()

            if (telefono.isNotEmpty()) {
                prefs.edit().putString("telefono", telefono).apply()
                mostrarCargando()

            } else {
                Toast.makeText(this, "Introduce un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarCargando() {
        btnGuardar.isEnabled = false
        progresoCircular.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
