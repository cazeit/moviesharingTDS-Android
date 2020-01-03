package de.tdsoftware.moviesharing.data.serialization.vimeo.movie


import com.squareup.moshi.Json

data class Metadata(
    @Json(name = "interactions")
    val interactions: Interactions
)