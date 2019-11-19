package de.tdsoftware.moviesharing.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import okhttp3.*
import okhttp3.Response
import java.lang.Exception


/**
 * No error handling so far, i had no clue how to approach.. this is very basic and very ugly...
 */
object NetworkManager {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun fetchAll(): Output<ArrayList<Playlist>> {
        val playlistResponse = fetchPlaylists()
        when (playlistResponse) {
            is Output.Success<PlaylistResponse> -> {
                val playlistList = mapToPlaylists(playlistResponse.data)
                for (playlist in playlistList) {
                    val movieResponse = fetchMovies(playlist.id)
                    when(movieResponse) {
                        is Output.Success<MovieResponse> -> {
                                var movieList = mapToMovies(movieResponse.data)
                                playlist.movieList.addAll(movieList)
                        }
                        is Output.Error -> {
                            return movieResponse
                        }
                    }
                }
                return Output.Success(playlistList)
            }
            is Output.Error -> {
                return playlistResponse
            }
        }
    }

    private fun fetchPlaylists(): Output<PlaylistResponse>{
        val responseOutput = fetchFromApi(buildPlaylistRequestUrl())
        when(responseOutput) {
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

    private fun fetchMovies(playlistId: String): Output<MovieResponse>{
        val responseOutput = fetchFromApi(buildPlaylistItemsRequestUrl(playlistId))
        when(responseOutput) {
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
            val client = OkHttpClient.Builder().build()
            val request = Request.Builder().url(url).build()
            val result = client.newCall(request).execute()
            Output.Success(result)
        } catch(exception: Exception){
            Output.Error("Error: $exception")
        }
    }

    private fun buildPlaylistRequestUrl(): HttpUrl {
        val url = HttpUrl.Builder().scheme("https").host("www.googleapis.com").addPathSegments("youtube/v3/playlists")
            .addQueryParameter("part","snippet").addQueryParameter("channelId", "UCPppOIczZfCCoqAwRLc4T0A")
            .addQueryParameter("maxResults", "50").addQueryParameter("key", "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU").build()
        return url
    }

    private fun buildPlaylistItemsRequestUrl(playlistId: String): HttpUrl {
        val url = HttpUrl.Builder().scheme("https").host("www.googleapis.com").addPathSegments("youtube/v3/playlistItems")
            .addQueryParameter("part","snippet").addQueryParameter("playlistId", playlistId)
            .addQueryParameter("maxResults", "50").addQueryParameter("key", "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU").build()
        return url
    }

    private fun mapToPlaylists(playlistResponse: PlaylistResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for(responseItem in playlistResponse.items){
            returnList.add(Playlist(responseItem.id, responseItem.snippet.title, ArrayList<Movie>()))
        }
        return returnList
    }

    private fun mapToMovies(movieResponse: MovieResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        for(responseItem in movieResponse.items){
            val secondaryText = "hinzugefügt am: " + responseItem.snippet.publishedAt.substring(8, 10) + "." +
                    responseItem.snippet.publishedAt.substring(5, 7) + "." +
                    responseItem.snippet.publishedAt.substring(0, 4)
            val imageString = responseItem.snippet.thumbnails.high.url
            returnList.add(Movie(responseItem.snippet.resourceId.videoId,responseItem.snippet.title, responseItem.snippet.description, secondaryText, imageString))
        }
        return returnList
    }

    // check connection in activity??? (LoadingActivity)
}