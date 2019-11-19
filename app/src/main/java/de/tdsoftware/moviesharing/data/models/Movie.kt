package de.tdsoftware.moviesharing.data.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Movie(val id: String,
                 val title: String,
                 val description: String,
                 val secondaryText: String,
                 var imagePath: String): Serializable