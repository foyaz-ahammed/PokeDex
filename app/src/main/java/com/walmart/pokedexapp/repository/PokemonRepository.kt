package com.walmart.pokedexapp.repository

import android.util.Log
import com.walmart.pokedexapp.repository.api.PokeAPI
import com.walmart.pokedexapp.repository.entities.DataResult
import com.walmart.pokedexapp.repository.entities.Response
import java.lang.Exception

/**
 * Repository class to load data from the network service
 */
class PokemonRepository(private val api: PokeAPI) {

    companion object {
        const val TAG = "PokemonRepository"
    }

    suspend fun getPokemonList(): DataResult<List<Response.PokeItem>> =
        try {
            val pokemonData = api.getPokemonList()
            DataResult.Success(pokemonData.pokemonList)
        } catch (e: Exception) {
            Log.w(TAG, "error: $e");
            DataResult.Failure(e)
        }

    suspend fun getPokemonDetail(name: String): DataResult<Response.PokeDetailData> =
        try {
            val pokeData = api.getPokemonDetail(name)
            DataResult.Success(pokeData)
        } catch (e: Exception) {
            Log.w(TAG, "error: $e");
            DataResult.Failure(e)
        }
}