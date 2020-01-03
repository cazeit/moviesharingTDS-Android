package de.tdsoftware.moviesharing.data.serialization.youtube.movie


import com.squareup.moshi.Json

data class Medium(
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int
)