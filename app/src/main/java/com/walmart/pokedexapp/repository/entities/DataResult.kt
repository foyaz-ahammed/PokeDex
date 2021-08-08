package com.walmart.pokedexapp.repository.entities

import java.lang.Exception

/**
 * Data result with the status of success and failure
 */
sealed class DataResult<T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Failure<T>(val exception: Exception): DataResult<T>()
}
