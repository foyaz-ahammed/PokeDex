package com.walmart.pokedexapp.repository.entities

import java.lang.Exception

sealed class DataResult<T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Failure<T>(val exception: Exception): DataResult<T>()
}
