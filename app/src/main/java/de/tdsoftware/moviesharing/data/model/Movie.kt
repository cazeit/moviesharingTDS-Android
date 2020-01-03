package de.tdsoftware.moviesharing.data.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * Model for a movie
 */
@JsonClass(generateAdapter = true)
data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val secondaryText: String,
    var imageUrl: String?,
    var isFavorite: Boolean = false
) : Serializable