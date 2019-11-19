package de.tdsoftware.moviesharing.util

sealed class Output<out T> {
    data class Success<out T: Any>(val data: T): Output<T>()
    data class Error(val exception: String): Output<Nothing>()
}