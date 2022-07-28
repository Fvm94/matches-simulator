package com.fvm.matchessimulator.ui

import android.content.Intent
import android.nfc.tech.Ndef.get
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fvm.matchessimulator.databinding.ActivityDetailBinding
import com.fvm.matchessimulator.domain.Match

class DetailActivity : AppCompatActivity() {

    object Extras {
        const val MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        getMatchesFromExtras()

    }

    private fun getMatchesFromExtras() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let {

            supportActionBar?.title = it.place.nome

            Glide.with(this).load(it.place.imagem).into(binding.ivPlace)

            Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)
            Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)

            binding.tvHomeTeamName.text = it.homeTeam.name
            binding.tvAwayTeamName.text = it.awayTeam.name

            if (it.homeTeam.score != null && it.homeTeam.score != null) {
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
                binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
            }
            binding.rbHomeTeamStars.rating = it.homeTeam.force.toFloat()
            binding.rbAwayTeamStars.rating = it.awayTeam.force.toFloat()
        }
    }
}