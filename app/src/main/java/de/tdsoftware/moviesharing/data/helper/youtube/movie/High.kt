package de.tdsoftware.moviesharing.data.helper.youtube.movie


import com.squareup.moshi.Json

data class High(
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int
)