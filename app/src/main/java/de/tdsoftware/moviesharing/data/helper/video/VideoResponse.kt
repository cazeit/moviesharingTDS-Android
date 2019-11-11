package de.tdsoftware.moviesharing.data.helper.video


import com.squareup.moshi.Json

data class VideoResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String
)