package com.walmart.pokedexapp.helper

import androidx.annotation.ColorRes
import com.walmart.pokedexapp.R
import com.walmart.pokedexapp.repository.entities.Response

fun Int.prettyPrintNo(): String = String.format("#%03d", this)
fun String.capitalizeFirst(): String = replaceFirstChar { it.uppercase() }
fun String.getNo(): Int {
    return dropLast(1).takeLastWhile { it.isDigit() }.toInt()
}
fun List<Response.PokeItem>.mapIndexAdded() = map { Response.PokeItem(it.url.getNo(), it.name, it.url) }

fun Int.formatHeight(): String = String.format("%d cm", this*10)
fun Int.formatWeight(): String = String.format("%d.%d kg", this/10, this%10)

fun Response.PokeTypeData.typeName(): String = this.type.name

@ColorRes
fun Response.PokeTypeData.getTypeColor() =
    when(typeName()) {
        "fighting" -> R.color.fighting
        "flying" -> R.color.flying
        "poison" -> R.color.poison
        "ground" -> R.color.ground
        "rock" -> R.color.rock
        "bug" -> R.color.bug
        "ghost" -> R.color.ghost
        "steel" -> R.color.steel
        "fire" -> R.color.fire
        "water" -> R.color.water
        "grass" -> R.color.grass
        "electric" -> R.color.electric
        "psychic" -> R.color.psychic
        "ice" -> R.color.ice
        "dragon" -> R.color.dragon
        "fairy" -> R.color.fairy
        "dark" -> R.color.dark
        else -> R.color.gray_21
    }