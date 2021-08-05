package com.walmart.pokedexapp.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.walmart.pokedexapp.databinding.ActivityDetailBinding
import com.walmart.pokedexapp.helper.capitalizeFirst
import com.walmart.pokedexapp.helper.formatHeight
import com.walmart.pokedexapp.helper.formatWeight
import com.walmart.pokedexapp.helper.getTypeColor
import com.walmart.pokedexapp.helper.imageUrl
import com.walmart.pokedexapp.helper.onEndLoading
import com.walmart.pokedexapp.helper.swapVisibility
import com.walmart.pokedexapp.helper.typeName
import com.walmart.pokedexapp.repository.entities.LoadResult
import com.walmart.pokedexapp.repository.entities.Response
import com.walmart.pokedexapp.viewmodels.PokeDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModel<PokeDetailViewModel>()

    companion object {
        const val POKE_NAME = "poke_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getPokeData().observe(this) {
            updateViews(it)
        }

        viewModel.loading().observe(this) {
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.pokeGroup.isVisible = it == LoadResult.SUCCESS
            binding.errorPoke.isVisible = it == LoadResult.FAIL
        }

        val pokeName = intent.getStringExtra(POKE_NAME)?:""
        viewModel.loadData(pokeName)
    }

    private fun updateViews(item: Response.PokeDetailData) {
        Glide.with(applicationContext)
            .load(item.imageUrl())
            .fitCenter()
            .into(binding.photo)
        binding.name.text = item.name.capitalizeFirst()
        binding.weight.text = item.weight.formatWeight()
        binding.height.text = item.height.formatHeight()

        binding.types.removeAllViews()
        item.types.sortedBy { it.typeName() }.forEach { type ->
            val chip = Chip(this).apply {
                text = type.typeName().capitalizeFirst()
                setTextColor(Color.WHITE)
                setChipBackgroundColorResource(type.getTypeColor())
            }

            binding.types.addView(chip)
        }

        binding.loadingLayout.alpha = 1f
        binding.loadingLayout.visibility = View.VISIBLE
        binding.photo.doOnPreDraw {
            val gifSize = binding.photo.measuredWidth

            Glide.with(this)
                .load(item.imageUrl())
                .override(gifSize, gifSize)
                .error(ColorDrawable(Color.GREEN))
                .fallback(ColorDrawable(Color.GREEN))
                .onEndLoading {
                    swapVisibility(
                        100,
                        binding.photo,
                        binding.loadingLayout
                    )
                }
                .into(binding.photo)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }
}