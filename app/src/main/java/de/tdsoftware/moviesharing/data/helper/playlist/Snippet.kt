package de.tdsoftware.moviesharing.data.helper.playlist


import com.squareup.moshi.Json

data class Snippet(
    @Json(name = "channelId")
    val channelId: String,
    @Json(name = "channelTitle")
    val channelTitle: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "title")
    val title: String
)