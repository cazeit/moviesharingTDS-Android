package de.tdsoftware.moviesharing.data.serialization.vimeo.movie


import com.squareup.moshi.Json

data class Size(
    @Json(name = "height")
    val height: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "width")
    val width: Int
)