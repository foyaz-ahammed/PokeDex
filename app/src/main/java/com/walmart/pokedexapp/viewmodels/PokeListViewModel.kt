package com.walmart.pokedexapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.pokedexapp.helper.mapIndexAdded
import com.walmart.pokedexapp.repository.PokemonRepository
import com.walmart.pokedexapp.repository.entities.DataResult
import com.walmart.pokedexapp.repository.entities.LoadResult
import com.walmart.pokedexapp.repository.entities.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokeListViewModel(private val repository: PokemonRepository): ViewModel() {
    private var cacheData:List<Response.PokeItem>? = null
    private val pokeItems = MutableLiveData<List<Response.PokeItem>>()
    private val loading = MutableLiveData<LoadResult>()

    fun getPokeItems(): LiveData<List<Response.PokeItem>> = pokeItems
    fun loading(): LiveData<LoadResult> = loading

    private suspend fun fetchData(): DataResult<List<Response.PokeItem>> =
        withContext(Dispatchers.IO) {
            return@withContext repository.getPokemonList()
        }

    fun loadData() {
        viewModelScope.launch {
            loading.value = LoadResult.LOADING
            when(val result = fetchData()) {
                is DataResult.Success -> {
                    cacheData = result.data.mapIndexAdded()
                    pokeItems.postValue(cacheData)
                    loading.value = LoadResult.SUCCESS
                }
                is DataResult.Failure -> {
                    cacheData = emptyList()
                    pokeItems.postValue(cacheData)
                    loading.value = LoadResult.FAIL
                }
            }
        }
    }

    fun search(query: String) {
        pokeItems.value =
            if(query.isEmpty()) cacheData
            else cacheData?.filter { it.name.contains(query, true) }
    }
}