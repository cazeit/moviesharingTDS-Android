package de.tdsoftware.moviesharing.data.helper.video


import com.squareup.moshi.Json

data class ResourceId(
    @Json(name = "kind")
    val kind: String,
    @Json(name = "videoId")
    val videoId: String
)