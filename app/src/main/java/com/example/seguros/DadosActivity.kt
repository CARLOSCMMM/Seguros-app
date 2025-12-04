package com.example.seguros

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seguros.databinding.ActivityDadosBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DadosActivity : AppCompatActivity() {
    private lateinit var bindingMain : ActivityDadosBinding
    private var sum : Int = 0
    private val handler = Handler(Looper.getMainLooper())
    private var userNumber: Int = 0

    /**
     * Este método se llama cuando se crea la actividad.
     * Es donde se inicializa la vista y se configuran los eventos iniciales.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityDadosBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        initEvent()
    }

    /**
     * Este método inicializa los eventos de la interfaz de usuario.
     * Principalmente, configura el botón para lanzar los dados.
     * Al hacer clic, comprueba si el número introducido es válido,
     * y si lo es, comienza el juego.
     */
    private fun initEvent() {
        bindingMain.txtResultado.visibility = View.INVISIBLE
        bindingMain.btnLanzar.setOnClickListener{
            val numberText = bindingMain.etNumero.text.toString()
            if (numberText.isNotEmpty()) {
                val number = numberText.toInt()
                if (number in 3..18) {
                    userNumber = number
                    bindingMain.btnLanzar.isEnabled = false
                    bindingMain.txtResultado.visibility = View.VISIBLE
                    game()
                } else {
                    Toast.makeText(this, "El número debe estar entre 3 y 18", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Introduce un número", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Este método inicia el juego.
     * Llama al método que programa el lanzamiento de los dados.
     */
    private fun game(){
        sheduleRun()
    }

    /**
     * Este método programa el lanzamiento de los dados.
     * Lanza los dados 5 veces, una vez por segundo.
     * Después de 7 segundos, muestra el resultado final.
     */
    private fun sheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 1000
        for (i in 1..5){
            schedulerExecutor.schedule(
                {
                    handler.post { throwDadoInTime() }
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        schedulerExecutor.schedule({
            handler.post { viewResult() }
        },
            msc  * 7.toLong(), TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }

    /**
     * Este método simula el lanzamiento de tres dados.
     * Genera tres números aleatorios entre 1 y 6.
     * Suma los resultados y actualiza las imágenes de los dados.
     */
    private fun throwDadoInTime() {
        val numDados = Array(3){ Random.nextInt(1, 7)}
        val imagViews : Array<ImageView> = arrayOf(
            bindingMain.imageviewDado1,
            bindingMain.imageviewDado2,
            bindingMain.imageviewDado3)

        sum = numDados.sum()
        for (i in 0..2)
            selectView(imagViews[i], numDados[i])
    }

    /**
     * Este método actualiza la imagen de un dado.
     * Dependiendo del número que ha salido,
     * muestra la imagen del dado correspondiente.
     */
    private fun selectView(imgV: ImageView, v: Int) {
        when (v){
            1 -> imgV.setImageResource(R.drawable.dado1)
            2 -> imgV.setImageResource(R.drawable.dado2)
            3 -> imgV.setImageResource(R.drawable.dado3)
            4 -> imgV.setImageResource(R.drawable.dado4)
            5 -> imgV.setImageResource(R.drawable.dado5)
            6 -> imgV.setImageResource(R.drawable.dado6)
        }
    }

    /**
     * Este método muestra el resultado final del juego.
     * Muestra la suma total de los dados.
     * Si la suma coincide con el número del usuario,
     * se abre una nueva pantalla de resultado.
     * Finalmente, vuelve a habilitar el botón de lanzar.
     */
    private fun viewResult() {
        bindingMain.txtResultado.text = sum.toString()
        if (sum == userNumber) {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
        bindingMain.btnLanzar.isEnabled = true
    }
}
