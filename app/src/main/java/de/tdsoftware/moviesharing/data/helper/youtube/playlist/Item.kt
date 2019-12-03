package de.tdsoftware.moviesharing.data.helper.youtube.playlist


import com.squareup.moshi.Json

data class Item(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "snippet")
    val snippet: Snippet
)