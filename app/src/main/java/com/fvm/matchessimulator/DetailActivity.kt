package com.fvm.matchessimulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fvm.matchessimulator.databinding.ActivityDetailBinding
import com.fvm.matchessimulator.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}