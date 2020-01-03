package de.tdsoftware.moviesharing.data.serialization.youtube.movie


import com.squareup.moshi.Json

data class ResourceId(
    @Json(name = "kind")
    val kind: String,
    @Json(name = "videoId")
    val videoId: String
)