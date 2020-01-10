package de.tdsoftware.moviesharing.data.serialization.vimeo.movie


import com.squareup.moshi.Json

data class Pictures(
    @Json(name = "active")
    val active: Boolean,
    @Json(name = "resource_key")
    val resourceKey: String,
    @Json(name = "sizes")
    val sizes: List<Size>,
    @Json(name = "type")
    val type: String,
    @Json(name = "uri")
    val uri: String
)