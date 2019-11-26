package de.tdsoftware.moviesharing.data.helper.playlist


import com.squareup.moshi.Json
import de.tdsoftware.moviesharing.data.helper.YouTubeApiResponse

/**
 * represents serialization-template for API-response for PlaylistRequest
 */
data class PlaylistResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name="nextPageToken")
    val nextPageToken: String?
): YouTubeApiResponse()