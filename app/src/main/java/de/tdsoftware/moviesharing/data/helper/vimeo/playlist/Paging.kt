package de.tdsoftware.moviesharing.data.helper.vimeo.playlist


import com.squareup.moshi.Json

data class Paging(
    @Json(name = "first")
    val first: String,
    @Json(name = "last")
    val last: String,
    @Json(name = "next")
    val next: String?,
    @Json(name = "previous")
    val previous: String?
)