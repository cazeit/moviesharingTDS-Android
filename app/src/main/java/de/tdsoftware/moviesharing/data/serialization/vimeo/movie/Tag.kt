package de.tdsoftware.moviesharing.data.serialization.vimeo.movie


import com.squareup.moshi.Json

data class Tag(
    @Json(name = "canonical")
    val canonical: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "resource_key")
    val resourceKey: String,
    @Json(name = "tag")
    val tag: String,
    @Json(name = "uri")
    val uri: String
)