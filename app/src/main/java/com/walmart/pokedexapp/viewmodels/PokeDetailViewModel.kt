package com.walmart.pokedexapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.pokedexapp.repository.PokemonRepository
import com.walmart.pokedexapp.repository.entities.DataResult
import com.walmart.pokedexapp.repository.entities.LoadResult
import com.walmart.pokedexapp.repository.entities.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * [ViewModel] used on poke detail screen
 */
class PokeDetailViewModel(private val repository: PokemonRepository): ViewModel() {
    private val pokeData = MutableLiveData<Response.PokeDetailData>()
    private val loading = MutableLiveData<LoadResult>()

    fun getPokeData(): LiveData<Response.PokeDetailData> = pokeData
    fun loading(): LiveData<LoadResult> = loading

    fun loadData(name: String) {
        viewModelScope.launch {
            loading.value = LoadResult.LOADING
            when(val result = repository.getPokemonDetail(name)) {
                is DataResult.Success -> {
                    pokeData.postValue(result.data)
                    loading.value = LoadResult.SUCCESS
                }
                is DataResult.Failure -> {
                    loading.value = LoadResult.FAIL
                }
            }
        }
    }
}