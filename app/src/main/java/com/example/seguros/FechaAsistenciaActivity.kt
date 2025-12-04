package com.example.seguros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast

class FechaAsistenciaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fecha_asistencia)

        val datePicker = findViewById<DatePicker>(R.id.datePickerAsistencia)
        val btnGuardarFecha = findViewById<Button>(R.id.btnGuardarFecha)

        btnGuardarFecha.setOnClickListener {
            val dia = datePicker.dayOfMonth
            val mes = datePicker.month + 1
            val año = datePicker.year
            Toast.makeText(this, "Fecha seleccionada: $dia/$mes/$año", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
