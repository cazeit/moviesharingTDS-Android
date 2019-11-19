package de.tdsoftware.moviesharing.data.helper.playlist


import com.squareup.moshi.Json

data class PlaylistResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name="nextPageToken")
    val nextPageToken: String?
)