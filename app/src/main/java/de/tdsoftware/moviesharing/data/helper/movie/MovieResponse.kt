package de.tdsoftware.moviesharing.data.helper.movie


import com.squareup.moshi.Json
import de.tdsoftware.moviesharing.data.helper.YouTubeApiResponse

/**
 * represents deserialization-template for API-response for MoviesRequest
 */
data class MovieResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "nextPageToken")
    val nextPageToken: String?
): YouTubeApiResponse()