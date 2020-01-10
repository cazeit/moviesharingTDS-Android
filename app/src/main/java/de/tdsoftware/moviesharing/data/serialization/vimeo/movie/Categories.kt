package de.tdsoftware.moviesharing.data.serialization.vimeo.movie

import com.squareup.moshi.Json

data class Categories(
    @Json(name = "name")
    val name: String
)