package com.walmart.pokedexapp.repository.api

import com.walmart.pokedexapp.helper.DEFAULT_LIMIT
import com.walmart.pokedexapp.helper.DEFAULT_OFFSET
import com.walmart.pokedexapp.repository.entities.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = DEFAULT_OFFSET
    ): Response.PokemonData

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): Response.PokeDetailData
}