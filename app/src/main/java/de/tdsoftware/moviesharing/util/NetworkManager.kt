package de.tdsoftware.moviesharing.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

/**
 * Singleton, that handles all networking (API-calls) and
 */
object NetworkManager: CoroutineScope {

    // region properties

    private const val CHANNEL_ID = "UCPppOIczZfCCoqAwRLc4T0A"
    private const val API_KEY = "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val job = Job()
    override val coroutineContext = Dispatchers.Default + job

    // endregion

    // region public API
    fun fetchAll(callback: (Result<ArrayList<Playlist>>) -> Unit){
        launch {
            val playlistList = ArrayList<Playlist>()
            var playlistPageToken: String? = ""
            var moviePageToken: String? = ""
            do {
                when (val playlistResponse = fetchPlaylistListFromUser(playlistPageToken)) {
                    is Result.Success<PlaylistResponse> -> {
                        playlistList.addAll(mapToPlaylists(playlistResponse.data))
                        for (playlist in playlistList) {
                            do {
                                when (val movieResponse = fetchMoviesFromPlaylist(playlist.id, moviePageToken)) {
                                    is Result.Success<MovieResponse> -> {
                                        val movieList = mapToMovies(movieResponse.data)
                                        playlist.movieList.addAll(movieList)
                                        moviePageToken = movieResponse.data.nextPageToken
                                    }
                                    is Result.Error -> {
                                        callback(movieResponse)
                                        return@launch
                                    }
                                }
                            } while (moviePageToken != null)
                        }
                        playlistPageToken = playlistResponse.data.nextPageToken
                    }
                    is Result.Error -> {
                        callback(playlistResponse)
                        return@launch
                    }
                }
            } while (playlistPageToken != null)
            callback(Result.Success(playlistList))
        }
    }

    // endregion

    // region private API

    private fun fetchPlaylistListFromUser(pageToken: String?): Result<PlaylistResponse> {
        when (val responseOutput = fetchFromApi(buildPlaylistRequestUrl(CHANNEL_ID, pageToken))) {
            is Result.Success -> {
                val response = responseOutput.data
                if (response.isSuccessful) {
                    val playlistString = response.body()?.string()
                    playlistString?.let {
                        val jsonAdapter = moshi.adapter(PlaylistResponse::class.java)
                        val playlistResponse = jsonAdapter.fromJson(playlistString)
                        if (playlistResponse != null) {
                            return Result.Success(playlistResponse)
                        }
                    }
                } else {
                    return Result.Error(400,"Error-Code from API while fetching playlists: " + response.code().toString())
                }
            }
            is Result.Error -> {
                return responseOutput
            }
        }
        return Result.Error(200, "API-Call unsuccessful!")
    }

    private fun fetchMoviesFromPlaylist(playlistId: String, pageToken: String?): Result<MovieResponse> {
        when (val responseOutput = fetchFromApi(buildPlaylistItemsRequestUrl(playlistId, pageToken))) {
            is Result.Success -> {
                val response = responseOutput.data
                if (response.isSuccessful) {
                    val movieString = response.body()?.string()
                    movieString?.let {
                        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
                        val movieResponse = jsonAdapter.fromJson(movieString)
                        if (movieResponse != null) {
                            return Result.Success(movieResponse)
                        }
                    }
                } else {
                    return Result.Error(400, "Error-Code from API while fetching movies: " + response.code().toString())
                }
            }
            is Result.Error -> {
                return responseOutput
            }
        }
        return Result.Error(200, "API-Call unsuccessful")
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

    private fun buildPlaylistItemsRequestUrl(playlistId: String, pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlistItems")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("playlistId", playlistId).addQueryParameter("maxResults", "50")
            .addQueryParameter("key", API_KEY).build()
    }

    private fun mapToPlaylists(playlistResponse: PlaylistResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for (responseItem in playlistResponse.items) {
            returnList.add(Playlist(responseItem.id, responseItem.snippet.title, ArrayList()))
        }
        return returnList
    }

    private fun mapToMovies(movieResponse: MovieResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        for (responseItem in movieResponse.items) {
            val secondaryText =
                "added on: " + responseItem.snippet.publishedAt.substring(8, 10) + "." +
                        responseItem.snippet.publishedAt.substring(5, 7) + "." +
                        responseItem.snippet.publishedAt.substring(0, 4)
            val imageString = responseItem.snippet.thumbnails.high.url
            returnList.add(
                Movie(
                    responseItem.snippet.resourceId.videoId,
                    responseItem.snippet.title,
                    responseItem.snippet.description,
                    secondaryText,
                    imageString
                )
            )
        }
        return returnList
    }

    // endregion
}