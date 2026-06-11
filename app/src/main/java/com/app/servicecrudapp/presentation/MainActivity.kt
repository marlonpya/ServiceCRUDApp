package com.app.servicecrudapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.servicecrudapp.databinding.ActivityMainBinding

// Activity host: solo infla el layout que contiene el NavHostFragment — sin lógica de negocio
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
