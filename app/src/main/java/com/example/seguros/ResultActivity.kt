package com.example.seguros

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var tvChiste: TextView
    private lateinit var btnRepetir: Button
    private var chisteActual: String = ""
    private val MYTAG = "TTS_CHISTE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvChiste = findViewById(R.id.tvChiste)
        btnRepetir = findViewById(R.id.btnRepetirChiste)

        configureTextToSpeech()

        val resultado = intent.getIntExtra("resultadoDados", -1)

        chisteActual = getChisteForNumber(resultado)
        tvChiste.text = chisteActual

        tvChiste.postDelayed({
            speakMeDescription(chisteActual)
        }, 500)

        btnRepetir.setOnClickListener {
            speakMeDescription(chisteActual)
        }
    }

    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext) {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
                Log.i(MYTAG, "TextToSpeech configurado correctamente")
            } else {
                Log.i(MYTAG, "Error configurando TextToSpeech")
            }
        }
    }

    private fun speakMeDescription(texto: String) {
        textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun getChisteForNumber(numero: Int): String {
        if (numero == -1) return "No he recibido ningún número, pero igual te cuento un chiste de todas formas."

        return when (numero % 5) { // 5 chistes distintos
            0 -> "¿Sabes cuál es el colmo de un asegurado? Tener mala cobertura hasta en el móvil."
            1 -> "Cariño, ¿crees en el amor a primera vista? Claro, por eso contraté el seguro antes de verte conducir."
            2 -> "He contratado un seguro contra la pereza... pero me da pereza leer la póliza."
            3 -> "Mi coche está tan viejo que, cuando llamé al seguro, me ofrecieron un seguro de vida en vez de uno de auto."
            else -> "¿Sabes cuál es el súper héroe favorito de las aseguradoras? ¡Siniestro Total!"
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
