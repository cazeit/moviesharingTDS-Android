package de.tdsoftware.moviesharing.data.helper.vimeo.playlist


import com.squareup.moshi.Json

data class Data(
    @Json(name = "created_time")
    val createdTime: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "link")
    val link: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "uri")
    val uri: String
)