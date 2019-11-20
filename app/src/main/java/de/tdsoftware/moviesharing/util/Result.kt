package de.tdsoftware.moviesharing.util

sealed class Result<out T> {
    data class Success<out T: Any>(val data: T): Result<T>()
    data class Error(val code: Int, val message: String): Result<Nothing>()
}