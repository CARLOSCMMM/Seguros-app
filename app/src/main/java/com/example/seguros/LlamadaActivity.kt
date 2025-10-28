package com.example.seguros

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.seguros.databinding.ActivityLlamadaBinding

class LlamadaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLlamadaBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLlamadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        val telefonoGuardado = prefs.getString("telefono", "")
        binding.txtPhone.text = telefonoGuardado ?: "No hay número guardado"

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                makeCall()
            } else {
                Toast.makeText(this, "Permiso necesario", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button.setOnClickListener {
            checkPermissionAndCall()
        }

        binding.ivCambiarPhone.setOnClickListener {
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkPermissionAndCall() {
        val telefono = binding.txtPhone.text.toString()
        if (telefono.isEmpty()) {
            Toast.makeText(this, "No hay número guardado", Toast.LENGTH_SHORT).show()
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                makeCall()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            makeCall()
        }
    }

    private fun makeCall() {
        val telefono = binding.txtPhone.text.toString()
        if (telefono.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telefono"))
            startActivity(intent)
        } else {
            Toast.makeText(this, "Número no válido", Toast.LENGTH_SHORT).show()
        }
    }
}
