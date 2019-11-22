package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import okhttp3.Response

class PlaylistRequest(private val pageToken: String, private val callback: (Result<PlaylistResponse>) -> Unit): Request() {

    companion object {
        private const val CHANNEL_ID = "UCPppOIczZfCCoqAwRLc4T0A"
    }

    override fun fetch(){
        launch {
            when (val playlistResponse = fetchFromApi(buildPlaylistRequestUrl(pageToken))) {
                is Result.Success -> {
                    val response = playlistResponse.data
                    checkPlaylisteResponse(response)
                    super.onFetched()
                }
                is Result.Error -> {
                    callback(playlistResponse)
                    super.onFetched()
                }
            }
        }
    }

    private fun checkPlaylisteResponse(response: Response){
        if (response.isSuccessful) {
            val playlistString = response.body()?.string()
            playlistString?.let {
                val jsonAdapter = moshi.adapter(PlaylistResponse::class.java)
                val playlistResponse = jsonAdapter.fromJson(playlistString)
                if (playlistResponse != null) {
                    callback(Result.Success(playlistResponse))
                }
            }
        } else {
            callback(Result.Error(400, "Error-Code from API while fetching movies: " + response.code().toString()))
        }
    }

    private fun buildPlaylistRequestUrl(pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlists")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("channelId", CHANNEL_ID)
            .addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }
}