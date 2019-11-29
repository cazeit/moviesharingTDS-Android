package de.tdsoftware.moviesharing.data.helper.youtube.playlist


import com.squareup.moshi.Json

data class Snippet(
    @Json(name = "channelId")
    val channelId: String,
    @Json(name = "channelTitle")
    val channelTitle: String,
    @Json(name = "title")
    val title: String
)