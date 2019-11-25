package de.tdsoftware.moviesharing.util.requests

import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.util.Result
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import okhttp3.Response

class PlaylistRequest(
    private val pageToken: String,
    private val callback: (Result<PlaylistResponse>) -> Unit
) : Request(pageToken != "") {

    companion object {
        private const val CHANNEL_ID = "UCPppOIczZfCCoqAwRLc4T0A"
        private const val CHANNEL_ID_SECOND = "UCvKt4C06Ap-YqbndvzRDLSA"
    }

    override fun fetch() {
        launch {
            when (val playlistResponse = fetchFromApi(buildPlaylistRequestUrl(pageToken))) {
                is Result.Success -> {
                    val response = playlistResponse.data
                    checkPlaylistResponse(response)
                    super.onFetched()
                }
                is Result.Error -> {
                    callback(playlistResponse)
                    super.onFetched()
                }
            }
        }
    }

    private fun checkPlaylistResponse(response: Response) {
        if (response.isSuccessful) {
            val playlistString = response.body()?.string()
            playlistString?.let {
                val jsonAdapter = moshi.adapter(PlaylistResponse::class.java)
                val playlistResponse = jsonAdapter.fromJson(playlistString)
                if (playlistResponse != null) {
                    callback(Result.Success(playlistResponse))
                }
            }
                ?: callback(Result.Error(150, "Error reading Server-Response."))
        } else {
            callback(
                Result.Error(
                    400,
                    "Error-Code from API while fetching playlists: " + response.code().toString()
                )
            )
        }
    }

    private fun buildPlaylistRequestUrl(pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlists")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter(
                "channelId",
                CHANNEL_ID
            )
            .addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }
}