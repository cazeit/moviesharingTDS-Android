package de.tdsoftware.moviesharing.data.helper.vimeo.movie

import com.squareup.moshi.Json

data class Categories(
    @Json(name = "name")
    val name: String
)