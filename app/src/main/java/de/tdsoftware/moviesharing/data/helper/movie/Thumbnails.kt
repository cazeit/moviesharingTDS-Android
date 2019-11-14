package de.tdsoftware.moviesharing.data.helper.movie


import com.squareup.moshi.Json

data class Thumbnails(
    @Json(name = "default")
    val default: Default,
    @Json(name = "high")
    val high: High,
    @Json(name = "medium")
    val medium: Medium
)