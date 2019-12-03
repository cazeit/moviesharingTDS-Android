package de.tdsoftware.moviesharing.util.requests.youtube

import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.data.helper.youtube.movie.YoutubeMovieResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class YoutubeMoviesRequest(
    private val playlistId: String,
    private val pageToken: String = "",
    private val callback: (Result<ApiResponse>) -> Unit
) : YoutubeRequest(callback) {

    // region Request-implementations

    override fun deserializeResponse(response: Response) {
        val movieString = response.body()?.string()
        movieString?.let {
            val jsonAdapter = moshi.adapter(YoutubeMovieResponse::class.java)
            val movieResponse = jsonAdapter.fromJson(movieString)
            if (movieResponse != null) {
                callback(Result.Success(movieResponse))
            }
        }
            ?: callback(Result.Error(150, "Error reading Server-Response."))
    }

    override fun buildRequestUrl(): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlistItems")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("playlistId", playlistId).addQueryParameter("maxResults", "50")
            .addQueryParameter(
                "key",
                API_KEY
            ).build()
    }

    // endregion

}