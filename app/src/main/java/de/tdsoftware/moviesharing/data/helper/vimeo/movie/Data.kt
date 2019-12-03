package de.tdsoftware.moviesharing.data.helper.vimeo.movie


import com.squareup.moshi.Json

data class Data(
    @Json(name = "description")
    val description: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "metadata")
    val metadata: Metadata,
    @Json(name = "name")
    val name: String,
    @Json(name = "pictures")
    val pictures: Pictures,
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "uri")
    val uri: String,
    @Json(name = "categories")
    val categories: List<Categories>
)