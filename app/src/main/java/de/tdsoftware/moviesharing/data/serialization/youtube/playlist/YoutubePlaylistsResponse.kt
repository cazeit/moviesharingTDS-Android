package de.tdsoftware.moviesharing.data.serialization.youtube.playlist

import com.squareup.moshi.Json
import de.tdsoftware.moviesharing.data.serialization.PlaylistsApiResponse

/**
 * Data class, that represents deserialization-template for API-response for YoutubePlaylistsRequest
 */
data class YoutubePlaylistsResponse(
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name="nextPageToken")
    val nextPageToken: String?
): PlaylistsApiResponse(nextPageToken)