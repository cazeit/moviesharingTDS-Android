package de.tdsoftware.moviesharing.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import okhttp3.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception


/**
 * bit confusing isn't it
 */

// TODO: GlobalScope austauschen.. bzw. nachlesen warum und wofür wir was anderes brauchen

object NetworkManager {

    // region properties
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // endregion

    // region public API
    fun fetchAll(callback: (Output<ArrayList<Playlist>>) -> Unit){
        GlobalScope.launch {
            val playlistList = ArrayList<Playlist>()
            var playlistPageToken: String? = ""
            var moviePageToken: String? = ""
            do {
                val playlistResponse = fetchPlaylists(playlistPageToken)
                when (playlistResponse) {
                    is Output.Success<PlaylistResponse> -> {
                        playlistList.addAll(mapToPlaylists(playlistResponse.data))
                        for (playlist in playlistList) {
                            do {
                                val movieResponse = fetchMovies(playlist.id, moviePageToken)
                                when (movieResponse) {
                                    is Output.Success<MovieResponse> -> {
                                        val movieList = mapToMovies(movieResponse.data)
                                        playlist.movieList.addAll(movieList)
                                        moviePageToken = movieResponse.data.nextPageToken
                                    }
                                    is Output.Error -> {
                                        callback(movieResponse)
                                    }
                                }
                            } while (moviePageToken != null)
                        }
                        playlistPageToken = playlistResponse.data.nextPageToken
                    }
                    is Output.Error -> {
                        callback(playlistResponse)
                    }
                }
            } while (playlistPageToken != null)
            callback(Output.Success(playlistList))
        }
    }

    // endregion

    // region private API
    private fun fetchPlaylists(pageToken: String?): Output<PlaylistResponse> {
        val responseOutput = fetchFromApi(buildPlaylistRequestUrl(pageToken))
        when (responseOutput) {
            is Output.Success -> {
                val response = responseOutput.data
                if (response.isSuccessful) {
                    val playlistString = response.body()?.string()
                    playlistString?.let {
                        val jsonAdapter = moshi.adapter(PlaylistResponse::class.java)
                        val playlistResponse = jsonAdapter.fromJson(playlistString)
                        if (playlistResponse != null) {
                            return Output.Success(playlistResponse)
                        }
                    }
                } else {
                    return Output.Error("Error-Code from API while fetching playlists: " + response.code().toString())
                }
            }
            is Output.Error -> {
                return responseOutput
            }
        }
        return Output.Error("API-Call unsuccessful!")
    }

    private fun fetchMovies(playlistId: String, pageToken: String?): Output<MovieResponse> {
        val responseOutput = fetchFromApi(buildPlaylistItemsRequestUrl(playlistId, pageToken))
        when (responseOutput) {
            is Output.Success -> {
                val response = responseOutput.data
                if (response.isSuccessful) {
                    val movieString = response.body()?.string()
                    movieString?.let {
                        val jsonAdapter = moshi.adapter(MovieResponse::class.java)
                        val movieResponse = jsonAdapter.fromJson(movieString)
                        if (movieResponse != null) {
                            return Output.Success(movieResponse)
                        }
                    }
                } else {
                    return Output.Error("Error-Code from API while fetching movies: " + response.code().toString())
                }
            }
            is Output.Error -> {
                return responseOutput
            }
        }
        return Output.Error("API-Call unsuccessful")
    }

    private fun fetchFromApi(url: HttpUrl): Output<Response> {
        return try {
            val httpClient = OkHttpClient.Builder().build()
            val request = Request.Builder().url(url).build()
            val result = httpClient.newCall(request).execute()
            Output.Success(result)
        } catch (exception: Exception) {
            Output.Error("Error: " + exception.message)
        }
    }

    private fun buildPlaylistRequestUrl(pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlists")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("channelId", "UCPppOIczZfCCoqAwRLc4T0A")
            .addQueryParameter("maxResults", "50")
            .addQueryParameter("key", "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU").build()
    }

    private fun buildPlaylistItemsRequestUrl(playlistId: String, pageToken: String?): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("www.googleapis.com")
            .addPathSegments("youtube/v3/playlistItems")
            .addQueryParameter("pageToken", pageToken).addQueryParameter("part", "snippet")
            .addQueryParameter("playlistId", playlistId).addQueryParameter("maxResults", "50")
            .addQueryParameter("key", "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU").build()
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
                "hinzugefügt am: " + responseItem.snippet.publishedAt.substring(8, 10) + "." +
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