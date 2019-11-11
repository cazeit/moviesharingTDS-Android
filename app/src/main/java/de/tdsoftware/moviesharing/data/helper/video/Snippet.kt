package de.tdsoftware.moviesharing.data.helper.video


import com.squareup.moshi.Json

data class Snippet(
    @Json(name = "channelId")
    val channelId: String,
    @Json(name = "channelTitle")
    val channelTitle: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "playlistId")
    val playlistId: String,
    @Json(name = "position")
    val position: Int,
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "resourceId")
    val resourceId: ResourceId,
    @Json(name = "thumbnails")
    val thumbnails: Thumbnails,
    @Json(name = "title")
    val title: String
)