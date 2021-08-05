package com.walmart.pokedexapp.repository.entities

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
        @Json(name = "sprites") val sprites: Sprites
    )

    @JsonClass(generateAdapter = true)
    class PokeTypeData (
        @Json(name = "type") val type: PokeType
    )

    @JsonClass(generateAdapter = true)
    class Sprites(
        @Json(name = "back_default") val back_default: String
    )

    @JsonClass(generateAdapter = true)
    class PokeType(
        @Json(name = "name") val name: String,
        @Json(name = "url") val url: String
    )
}