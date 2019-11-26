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

    private var isRequesting: Boolean = false

    // endregion

    // region public API
    /**
     * fetch a list of all playlists from user that is defined in PlaylistRequest (via channelId)
     */
    fun fetchPlaylistList(callback: (Result<ArrayList<Playlist>>) -> Unit) {
        val playlistList = ArrayList<Playlist>()
        fetchPlaylistsByToken(callback = callback, playlistList = playlistList)
    }

    /**
     * Fetch all movies a specific playlist contains (identified by playlistId as param)
     */
    fun fetchMoviesFromPlaylist(
        playlist: Playlist,
        callback: (Result<ArrayList<Movie>>) -> Unit
    ) {
        val movieList = ArrayList<Movie>()
        fetchMoviesForPlaylistByToken(
            playlist = playlist,
            movieList = movieList,
            callback = callback
        )
    }

    // endregion

    // region private API
    /**
     * Register a request to the requestQueue, with the possibility to add a request as the next item.
     * When registering to request-queue as next item, the request is being inserted after the current
     * one (pos. 1), so it gets started as soon as the first unregisters.
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
     * Unregister all pending requests (called when an error occurs)
     */
    private fun unregisterAllRequests() {
        requestQueue.clear()
        isRequesting = false
    }

    private fun fetchPlaylistsByToken(
        pageToken: String = "",
        playlistList: ArrayList<Playlist>,
        callback: (Result<ArrayList<Playlist>>) -> Unit
    ) {
        registerRequest(PlaylistRequest(pageToken) { playlistResponseResult ->
            when (playlistResponseResult) {
                is Result.Success -> {
                    val playlistResponse = playlistResponseResult.data as PlaylistResponse
                    playlistList.addAll(mapToPlaylistList(playlistResponse))
                    if (playlistResponse.nextPageToken != null) {
                        fetchPlaylistsByToken(
                            playlistResponse.nextPageToken,
                            playlistList,
                            callback
                        )
                    } else {
                        callback(Result.Success(playlistList))
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

    private fun fetchMoviesForPlaylistByToken(
        playlist: Playlist,
        pageToken: String = "",
        movieList: ArrayList<Movie>,
        callback: (Result<ArrayList<Movie>>) -> Unit
    ) {
        registerRequest(MoviesRequest(playlist.id, pageToken) { movieResponseResult ->
            when (movieResponseResult) {
                is Result.Success -> {
                    val movieResponse = movieResponseResult.data as MovieResponse
                    movieList.addAll(mapToMovies(movieResponseResult.data))
                    if (movieResponse.nextPageToken != null) {
                        fetchMoviesForPlaylistByToken(
                            playlist,
                            movieResponse.nextPageToken,
                            movieList,
                            callback
                        )
                    } else {
                        callback(Result.Success(movieList))
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

    private fun mapToPlaylistList(playlistResponse: PlaylistResponse): ArrayList<Playlist> {
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

    // endregion
}