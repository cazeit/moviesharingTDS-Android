package de.tdsoftware.moviesharing.data.helper.vimeo.movie


import com.squareup.moshi.Json

data class Interactions(
    @Json(name = "like")
    val like: Like
)