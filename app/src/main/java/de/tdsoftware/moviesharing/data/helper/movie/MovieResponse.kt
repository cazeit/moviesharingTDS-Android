package de.tdsoftware.moviesharing.data.helper.movie


import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "nextPageToken")
    val nextPageToken: String?
)