package de.tdsoftware.moviesharing.data.helper.movie


import com.squareup.moshi.Json

data class Default(
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int
)