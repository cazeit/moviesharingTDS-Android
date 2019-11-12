package de.tdsoftware.moviesharing.util

import java.lang.Exception

/**
 * first thoughts about Response-Tyoe that is planned to be connected to UI to check states..
 */
sealed class Response<out T: Any> {
    object Loading: Response<Nothing>()
    data class Success<out T: Any>(val result: T): Response<T>()
    data class Error(val exception: Exception): Response<Nothing>()
}