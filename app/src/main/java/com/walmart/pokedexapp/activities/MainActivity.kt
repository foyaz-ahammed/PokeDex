package com.walmart.pokedexapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.walmart.pokedexapp.R
import com.walmart.pokedexapp.adapters.PokeListAdapter
import com.walmart.pokedexapp.databinding.ActivityMainBinding
import com.walmart.pokedexapp.repository.entities.LoadResult
import com.walmart.pokedexapp.viewmodels.PokeListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<PokeListViewModel>()
    private val adapter = PokeListAdapter()
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pokeListRecyclerview.adapter = adapter

        viewModel.getPokeItems().observe(this) {
            adapter.submitList(it)
        }
        viewModel.loading().observe(this) {
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.pokeListRecyclerview.isVisible = it == LoadResult.SUCCESS
            binding.errorViews.isVisible = it == LoadResult.FAIL
        }

        viewModel.loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchItem = menu!!.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(query != newText) {
                    query = newText?:""
                    viewModel.search(query)
                }
                return true
            }
        })
        return true
    }

    fun onRetry(view: View) {
        viewModel.loadData()
    }
}