package de.tdsoftware.moviesharing.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import okhttp3.HttpUrl
import okhttp3.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

/**
 * Singleton, that handles all networking (API-calls) and
 */
object NetworkManager {

    // region properties

    private const val CHANNEL_ID = "UCPppOIczZfCCoqAwRLc4T0A"
    private const val API_KEY = "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // endregion

    // region public API

    fun fetchPlaylistListFromUser(pageToken: String?, callback: (Result<PlaylistResponse>) -> Unit) {
        when (val responseOutput = fetchFromApi(buildPlaylistRequestUrl(CHANNEL_ID, pageToken))) {
            is Result.Success -> {
                val response = responseOutput.data
                checkPlaylisteResponse(response, callback)
            }
            is Result.Error -> {
                callback(responseOutput)
            }
        }
    }

    fun fetchMoviesFromPlaylist(playlistId: String, pageToken: String?, callback: (Result<MovieResponse>) -> Unit){
        when (val responseOutput = fetchFromApi(buildMoviesFromPlaylistRequestUrl(playlistId, pageToken))) {
            is Result.Success -> {
                val response = responseOutput.data
                checkMovieResponse(response, callback)
            }
            is Result.Error -> {
                callback(responseOutput)
            }
        }
    }

    private fun fetchFromApi(url: HttpUrl): Result<Response> {
        return try {
            val httpClient = OkHttpClient.Builder().build()
            val request = Request.Builder().url(url).build()
            val result = httpClient.newCall(request).execute()
            Result.Success(result)
        } catch (exception: Exception) {
            Result.Error(100,"Error connecting. Check your connection!")
        }
    }

    private fun buildPlaylistRequestUrl(channelId: String, pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlists")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("channelId", channelId)
            .addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }

    /**
     * playlistItems = movies
     */
    private fun buildMoviesFromPlaylistRequestUrl(playlistId: String, pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlistItems")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("playlisId", playlistId).addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }

    private fun checkMovieResponse(response: Response, callback: (Result<MovieResponse>) -> Unit){
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

    private fun checkPlaylisteResponse(response: Response, callback: (Result<PlaylistResponse>) -> Unit){
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

    // endregion
}