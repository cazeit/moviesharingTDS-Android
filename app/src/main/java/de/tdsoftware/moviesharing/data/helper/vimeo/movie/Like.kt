package de.tdsoftware.moviesharing.data.helper.vimeo.movie


import com.squareup.moshi.Json

data class Like(
    @Json(name = "added")
    val added: Boolean,
    @Json(name = "added_time")
    val addedTime: String?,
    @Json(name = "options")
    val options: List<String>,
    @Json(name = "uri")
    val uri: String
)