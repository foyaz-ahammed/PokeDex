package com.walmart.pokedexapp.repository.entities

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network response classes
 */
class Response {
    @JsonClass(generateAdapter = true)
    class PokemonData (
        @Json(name = "results") val pokemonList: List<PokeItem>
    )

    @JsonClass(generateAdapter = true)
    class PokeItem (
        var number: Int = 0,
        @Json(name = "name") val name: String,
        @Json(name = "url") val url: String
    )

    @JsonClass(generateAdapter = true)
    class PokeDetailData (
        @Json(name = "id") @PrimaryKey val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "weight") val weight: Int,
        @Json(name = "height") val height: Int,
        @Json(name = "types") val types: List<PokeTypeData>,
        @Json(name = "sprites") val sprites: Sprites,
        @Json(name = "stats") val stats: List<State>,
    )

    @JsonClass(generateAdapter = true)
    class PokeTypeData (
        @Json(name = "type") val type: PokeType
    )

    @JsonClass(generateAdapter = true)
    class PokeType(
        @Json(name = "name") val name: String,
        @Json(name = "url") val url: String
    )

    @JsonClass(generateAdapter = true)
    class Sprites(
        @Json(name = "back_default") val back_default: String
    )

    @JsonClass(generateAdapter = true)
    class State(
        @Json(name = "base_stat") val base_stat: Int,
        @Json(name = "stat") val stat: Stat
    )

    @JsonClass(generateAdapter = true)
    class Stat(
        @Json(name = "name") val name: String
    )
}