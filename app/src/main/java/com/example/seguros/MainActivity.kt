package com.example.seguros

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
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

        btnConfig.setOnClickListener {
            startActivity(Intent(this, ConfiguracionActivity::class.java))
        }

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
            Toast.makeText(this, "Alarma programada para dentro de 2 minutos", Toast.LENGTH_SHORT).show()

            android.os.Handler(mainLooper).postDelayed({
                try {
                    val sonido = android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI
                    val ringtone = android.media.RingtoneManager.getRingtone(applicationContext, sonido)
                    ringtone.play()
                    Toast.makeText(this, "Alarma sonando", Toast.LENGTH_LONG).show()

                    android.os.Handler(mainLooper).postDelayed({
                        if (ringtone.isPlaying) {
                            ringtone.stop()
                            Toast.makeText(this, "Alarma detenida automáticamente a los 10 segundos", Toast.LENGTH_SHORT).show()
                        }
                    }, 10 * 1000)

                } catch (e: Exception) {
                    Toast.makeText(this, "Error al reproducir el sonido de alarma", Toast.LENGTH_SHORT).show()
                }
            }, 2 * 60 * 1000)
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
    }
}
