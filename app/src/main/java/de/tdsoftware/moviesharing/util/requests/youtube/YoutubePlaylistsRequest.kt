package de.tdsoftware.moviesharing.util.requests.youtube

import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.data.helper.youtube.playlist.YoutubePlaylistsResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class YoutubePlaylistsRequest(
    private val pageToken: String = "",
    private val callback: (Result<ApiResponse>) -> Unit
) : YoutubeRequest(callback) {

    // region Request-implementations

    override fun deserializeResponse(response: Response) {
        val playlistString = response.body()?.string()
        playlistString?.let {
            val jsonAdapter = moshi.adapter(YoutubePlaylistsResponse::class.java)
            val playlistResponse = jsonAdapter.fromJson(playlistString)
            if (playlistResponse != null) {
                callback(Result.Success(playlistResponse))
            }
        }
            ?: callback(Result.Error(150, "Error reading Server-Response."))
    }

    override fun buildRequestUrl(): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlists")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter(
                "channelId",
                CHANNEL_ID
            )
            .addQueryParameter("maxResults", "50")
            .addQueryParameter("key",
                API_KEY
            ).build()
    }

    // endregion
}