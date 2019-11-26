package de.tdsoftware.moviesharing.util.requests

import de.tdsoftware.moviesharing.data.helper.YouTubeApiResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class PlaylistRequest(
    private val pageToken: String,
    private val callback: (Result<YouTubeApiResponse>) -> Unit
) : Request(callback) {

    // region public types
    companion object {
        private const val CHANNEL_ID = "UCPppOIczZfCCoqAwRLc4T0A"
        private const val CHANNEL_ID_SECOND = "UCvKt4C06Ap-YqbndvzRDLSA"
    }
    // endregion

    // region Request-implementations
    override fun deserializeResponse(response: Response) {
        val playlistString = response.body()?.string()
        playlistString?.let {
            val jsonAdapter = moshi.adapter(PlaylistResponse::class.java)
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
                CHANNEL_ID_SECOND
            )
            .addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }

    // endregion
}