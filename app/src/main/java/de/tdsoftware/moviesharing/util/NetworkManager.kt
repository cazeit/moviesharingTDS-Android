package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.util.requests.MoviesRequest
import de.tdsoftware.moviesharing.util.requests.PlaylistRequest
import de.tdsoftware.moviesharing.util.requests.Request

/**
 * Singleton, that handles all networking
 */
object NetworkManager {

    // region properties
    private val requestQueue by lazy {
        ArrayList<Request>()
    }

    private val playlistList by lazy {
        ArrayList<Playlist>()
    }

    private val movieList by lazy {
        ArrayList<Movie>()
    }

    private var isRequesting: Boolean = false

    // endregion

    // region public API
    /**
     * fetch a list of all playlists from user that is defined in PlaylistRequest (channelId)
     */
    fun fetchPlaylistList(pageToken: String = "", callback: (Result<ArrayList<Playlist>>) -> Unit) {
        registerRequest(PlaylistRequest(pageToken) { playlistResponseResult ->
            when (playlistResponseResult) {
                is Result.Success -> {
                    val playlistResponse = playlistResponseResult.data as PlaylistResponse
                    playlistList.addAll(mapToPlaylistList(playlistResponse))
                    if (playlistResponse.nextPageToken != null) {
                        fetchPlaylistList(playlistResponse.nextPageToken, callback)
                    } else {
                        callback(Result.Success(playlistList))
                        // give free memory after..
                        playlistList.clear()
                    }
                    unregisterRequest(requestQueue.first())
                }
                is Result.Error -> {
                    callback(playlistResponseResult)
                    unregisterAllRequests()
                }
            }
        }, pageToken != "")
    }

    /**
     * fetch a list of all movies a specific playlist contains (identified by playlistId as param)
     */
    fun fetchMoviesFromPlaylist(
        playlist: Playlist,
        pageToken: String = "",
        callback: (Result<ArrayList<Movie>>) -> Unit
    ) {
        registerRequest(MoviesRequest(playlist.id, pageToken) { movieResponseResult ->
            when (movieResponseResult) {
                is Result.Success -> {
                    val movieResponse = movieResponseResult.data as MovieResponse
                    movieList.addAll(mapToMovies(movieResponseResult.data))
                    if (movieResponse.nextPageToken != null) {
                        fetchMoviesFromPlaylist(playlist, movieResponse.nextPageToken, callback)
                    } else {
                        callback(Result.Success(movieList))
                        // give free memory after..
                        movieList.clear()
                    }
                    unregisterRequest(requestQueue.first())
                }
                is Result.Error -> {
                    callback(movieResponseResult)
                    unregisterAllRequests()
                }
            }
        }, pageToken != "")
    }
    // endregion

    // region private API
    /**
     * Register a request to the requestQueue, with the possibility to add a request as the next item
     * When registering to request-queue, its inserted after the current one (pos. 1), so it gets started as soon
     * as the current one finishes
     */
    private fun registerRequest(request: Request, registerAsFirst: Boolean) {
        if (registerAsFirst && requestQueue.isNotEmpty()) {
            requestQueue.add(1, request)

        } else {
            requestQueue.add(request)
        }
        if (!isRequesting) {
            isRequesting = true
            requestQueue.first().fetch()
        }
    }

    /**
     * Unregister the request and start fetching the next one in line
     */
    private fun unregisterRequest(request: Request) {
        requestQueue.remove(request)
        if (requestQueue.isNotEmpty()) {
            requestQueue.first().fetch()
        } else {
            isRequesting = false
        }
    }

    /**
     * Unregister all pending requests (called when an error occurrs)
     */
    private fun unregisterAllRequests() {
        requestQueue.clear()
        isRequesting = false
    }

    private fun mapToMovies(movieResponse: MovieResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        for (responseItem in movieResponse.items) {
            val secondaryText =
                "added on: " + responseItem.snippet.publishedAt.substring(8, 10) + "." +
                        responseItem.snippet.publishedAt.substring(5, 7) + "." +
                        responseItem.snippet.publishedAt.substring(0, 4)
            val imageString = responseItem.snippet.thumbnails?.high?.url
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

    private fun mapToPlaylistList(playlistResponse: PlaylistResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for (responseItem in playlistResponse.items) {
            returnList.add(Playlist(responseItem.id, responseItem.snippet.title, ArrayList()))
        }
        return returnList
    }
    // endregion
}