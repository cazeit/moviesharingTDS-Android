package de.tdsoftware.moviesharing.util

import android.util.Log
import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import okhttp3.Response


class MoviesRequest(private val playlistId: String, private val pageToken: String, private val callback: (Result<MovieResponse>) -> Unit): Request() {

    companion object {
        private val TAG = MoviesRequest::class.java.simpleName
    }

    override fun fetch() {
        launch {
            Log.v(TAG,"Starting to fetch movies for playlist with ID: $playlistId")
            when(val movieResponse = fetchFromApi(buildMoviesFromPlaylistRequestUrl(playlistId, pageToken))) {
                is Result.Success -> {
                    val response = movieResponse.data
                    checkMovieResponse(response)
                    super.onFetched()
                }
                is Result.Error -> {
                    callback(movieResponse)
                    super.onFetched()
                }
            }
        }
    }

    private fun checkMovieResponse(response: Response) {
        if (response.isSuccessful) {
            val movieString = response.body()?.string()
            movieString?.let {
                val jsonAdapter = moshi.adapter(MovieResponse::class.java)
                val movieResponse = jsonAdapter.fromJson(movieString)
                if (movieResponse != null) {
                    callback(Result.Success(movieResponse))
                }
            }
        } else {
            callback(Result.Error(400, "Error-Code from API while fetching movies: " + response.code().toString()))
        }
    }

    private fun buildMoviesFromPlaylistRequestUrl(playlistId: String, pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlistItems")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("playlistId", playlistId).addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }

}