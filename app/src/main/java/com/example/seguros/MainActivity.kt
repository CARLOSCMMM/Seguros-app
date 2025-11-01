package com.example.seguros

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE)

        val btnLlamada = findViewById<ImageButton>(R.id.btnLlamada)
        val btnUrl = findViewById<ImageButton>(R.id.btnUrl)
        val btnAlarma = findViewById<ImageButton>(R.id.btnAlarma)
        val btnMaps = findViewById<ImageButton>(R.id.btnMaps)
        val btnConfig = findViewById<ImageButton>(R.id.btnConfiguracion)

        btnLlamada.setOnClickListener {
            val telefono = prefs.getString("telefono", "000000000")
            val intent = Intent(this, LlamadaActivity::class.java)
            intent.putExtra("telefono", telefono)
            startActivity(intent)
        }

        btnUrl.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mapfre.es"))
            startActivity(urlIntent)
        }

        btnAlarma.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma de Seguros")
                putExtra(AlarmClock.EXTRA_LENGTH, 120)
                putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                Toast.makeText(this, "Temporizador programado para 2 minutos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se puede programar un temporizador", Toast.LENGTH_SHORT).show()
            }
        }

        btnMaps.setOnClickListener {
            val latitud = 37.7795
            val longitud = -3.7853
            val etiqueta = "MAPFRE Jaén"
            val gmmIntentUri = Uri.parse("geo:$latitud,$longitud?q=$etiqueta")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        btnConfig.setOnClickListener {
            startActivity(Intent(this, ConfiguracionActivity::class.java))
        }
    }
}
