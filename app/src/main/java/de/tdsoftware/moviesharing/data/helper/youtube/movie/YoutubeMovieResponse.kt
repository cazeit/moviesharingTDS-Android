package de.tdsoftware.moviesharing.data.helper.youtube.movie


import com.squareup.moshi.Json
import de.tdsoftware.moviesharing.data.helper.MoviesApiResponse

/**
 * Data class, that represents deserialization-template for API-response for YoutubeMoviesRequest
 */
data class YoutubeMovieResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "nextPageToken")
    val nextPageToken: String?
): MoviesApiResponse(nextPageToken)