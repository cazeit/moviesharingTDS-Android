package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.serialization.ApiResponse
import de.tdsoftware.moviesharing.data.serialization.MoviesApiResponse
import de.tdsoftware.moviesharing.data.serialization.PlaylistsApiResponse
import de.tdsoftware.moviesharing.data.serialization.vimeo.favorite.VimeoFavoriteResponse
import de.tdsoftware.moviesharing.data.serialization.vimeo.movie.VimeoMoviesResponse
import de.tdsoftware.moviesharing.data.serialization.vimeo.playlist.VimeoPlaylistsResponse
import de.tdsoftware.moviesharing.data.serialization.youtube.movie.YoutubeMovieResponse
import de.tdsoftware.moviesharing.data.serialization.youtube.playlist.YoutubePlaylistsResponse
import de.tdsoftware.moviesharing.data.model.Movie
import de.tdsoftware.moviesharing.data.model.Playlist
import de.tdsoftware.moviesharing.util.requests.youtube.YoutubeMoviesRequest
import de.tdsoftware.moviesharing.util.requests.youtube.YoutubePlaylistsRequest
import de.tdsoftware.moviesharing.util.requests.Request
import de.tdsoftware.moviesharing.util.requests.vimeo.VimeoLikeRequest
import de.tdsoftware.moviesharing.util.requests.vimeo.VimeoMoviesRequest
import de.tdsoftware.moviesharing.util.requests.vimeo.VimeoPlaylistsRequest
import de.tdsoftware.moviesharing.util.requests.vimeo.VimeoUnlikeRequest

/**
 * Singleton, that handles all networking
 */
object NetworkManager {

    // region properties

    private val requestQueue by lazy {
        ArrayList<Request>()
    }

    private var isRequesting: Boolean = false

    lateinit var sourceApi: ApiName

    enum class ApiName {
        VIMEO, YOUTUBE
    }
    // endregion

    // region public API
    fun changeSourceApi(newSourceApi: ApiName) {
        sourceApi = newSourceApi
    }

    /**
     * fetch a list of all playlists from user that is defined in YoutubePlaylistsRequest (via channelId)
     */
    fun fetchPlaylistList(callback: (Result<ArrayList<Playlist>>) -> Unit) {
        val playlistList = ArrayList<Playlist>()
        fetchPlaylistsByNextPage(callback = callback, playlistList = playlistList)
    }

    /**
     * Fetch all movies a specific playlist contains (identified by playlistId as param)
     */
    fun fetchMoviesFromPlaylist(
        playlist: Playlist,
        callback: (Result<ArrayList<Movie>>) -> Unit
    ) {
        val movieList = ArrayList<Movie>()
        fetchMoviesForPlaylistByNextPage(
            playlist = playlist,
            movieList = movieList,
            callback = callback
        )
    }

    fun changeVimeoFavoriteStatus(
        movie: Movie,
        isFavorite: Boolean,
        callback: (Result<Boolean>) -> Unit
    ) {
        when(sourceApi) {
            ApiName.VIMEO -> {
                val favoriteCallback: (Result<ApiResponse>) -> Unit = { likeResponseResult ->
                    when (likeResponseResult) {
                        is Result.Success -> {
                            val favoriteResponse = likeResponseResult.data as VimeoFavoriteResponse
                            callback(Result.Success(favoriteResponse.isFavorite))
                        }
                        is Result.Error -> {
                            println("There was an error setting favorite state - launching callback")
                            callback(likeResponseResult)
                        }
                    }
                    unregisterRequest(requestQueue.first())
                }
                if (isFavorite) {
                    registerRequest(VimeoLikeRequest(movie.id, favoriteCallback), false)
                } else {
                    registerRequest(VimeoUnlikeRequest(movie.id, favoriteCallback), false)
                }
            }
            ApiName.YOUTUBE -> {
                callback(Result.Success(isFavorite))
            }
        }
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

    private fun fetchPlaylistsByNextPage(
        nextPage: String = "",
        playlistList: ArrayList<Playlist>,
        callback: (Result<ArrayList<Playlist>>) -> Unit
    ) {
        val playlistResponseCallback: (Result<ApiResponse>) -> Unit = { playlistResponseResult ->
            when (playlistResponseResult) {
                is Result.Success -> {
                    val playlistResponse = playlistResponseResult.data as PlaylistsApiResponse
                    playlistList.addAll(mapToPlaylistList(playlistResponse))
                    if (playlistResponse.nextPage != null) {
                        fetchPlaylistsByNextPage(
                            playlistResponse.nextPage,
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
        }
        when (sourceApi) {
            ApiName.YOUTUBE -> {
                registerRequest(
                    YoutubePlaylistsRequest(
                        callback = playlistResponseCallback,
                        pageToken = nextPage
                    ),
                    nextPage != ""
                )
            }
            ApiName.VIMEO -> {
                registerRequest(
                    VimeoPlaylistsRequest(
                        callback = playlistResponseCallback,
                        pageString = nextPage
                    ),
                    nextPage != ""
                )
            }
        }
    }

    private fun fetchMoviesForPlaylistByNextPage(
        playlist: Playlist,
        nextPage: String = "",
        movieList: ArrayList<Movie>,
        callback: (Result<ArrayList<Movie>>) -> Unit
    ) {
        val movieCallback: (Result<ApiResponse>) -> Unit = { movieResponseResult ->
            when (movieResponseResult) {
                is Result.Success -> {
                    val movieResponse = movieResponseResult.data as MoviesApiResponse
                    movieList.addAll(mapToMovies(movieResponse))
                    if (movieResponse.nextPage != null) {
                        fetchMoviesForPlaylistByNextPage(
                            playlist,
                            movieResponse.nextPage,
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
        }
        when (sourceApi) {
            ApiName.YOUTUBE -> {
                registerRequest(
                    YoutubeMoviesRequest(playlist.id, nextPage, movieCallback),
                    nextPage != ""
                )
            }
            ApiName.VIMEO -> {
                registerRequest(
                    VimeoMoviesRequest(playlist.id, nextPage, movieCallback),
                    nextPage != ""
                )
            }
        }
    }

    private fun mapToPlaylistList(playlistsApiResponse: PlaylistsApiResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        when (playlistsApiResponse) {
            is YoutubePlaylistsResponse -> {
                returnList.addAll(mapToPlaylistListFromYoutube(playlistsApiResponse))
            }
            is VimeoPlaylistsResponse -> {
                returnList.addAll(mapToPlaylistListFromVimeo(playlistsApiResponse))
            }
        }
        return returnList
    }

    private fun mapToPlaylistListFromYoutube(youtubePlaylistsResponse: YoutubePlaylistsResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for (responseItem in youtubePlaylistsResponse.items) {
            returnList.add(
                Playlist(
                    responseItem.id,
                    responseItem.snippet.title,
                    ArrayList()
                )
            )
        }
        return returnList
    }

    private fun mapToPlaylistListFromVimeo(vimeoPlaylistsResponse: VimeoPlaylistsResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for (responseItem in vimeoPlaylistsResponse.data) {
            val playlistId = responseItem.uri.substringAfterLast("/")
            returnList.add(
                Playlist(
                    playlistId, responseItem.name, ArrayList()
                )
            )
        }
        return returnList
    }


    private fun mapToMovies(moviesApiResponse: MoviesApiResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        when (moviesApiResponse) {
            is YoutubeMovieResponse -> {
                returnList.addAll(mapToMoviesFromYoutube(moviesApiResponse))
            }
            is VimeoMoviesResponse -> {
                returnList.addAll(mapToMoviesFromVimeo(moviesApiResponse))
            }
        }
        return returnList
    }

    private fun mapToMoviesFromYoutube(youtubeMoviesResponse: YoutubeMovieResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        for (responseItem in youtubeMoviesResponse.items) {
            val secondaryText =
                "added on: " + responseItem.snippet.publishedAt.substring(8, 10) + "." +
                        responseItem.snippet.publishedAt.substring(5, 7) + "." +
                        responseItem.snippet.publishedAt.substring(0, 4)
            val imageLink = responseItem.snippet.thumbnails?.high?.url
            returnList.add(
                Movie(
                    responseItem.snippet.resourceId.videoId,
                    responseItem.snippet.title,
                    responseItem.snippet.description,
                    secondaryText,
                    imageLink
                )
            )
        }
        return returnList
    }

    private fun mapToMoviesFromVimeo(vimeoMoviesResponse: VimeoMoviesResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        for (responseItem in vimeoMoviesResponse.data) {
            var secondaryText = ""
            var imageLink = ""
            var maxHeight = 0
            var count = 0
            if (responseItem.categories.isEmpty()) {
                secondaryText = "No categories found."
            } else {
                for (category in responseItem.categories) {
                    secondaryText += category.name
                    count++
                    if (count >= 3) {
                        break
                    }
                    secondaryText += ", "
                }
            }
            for (picture in responseItem.pictures.sizes) {
                if (picture.height > maxHeight) {
                    imageLink = picture.link
                    maxHeight = picture.height
                }
            }
            returnList.add(
                Movie(
                    responseItem.uri.substringAfterLast("/"),
                    responseItem.name,
                    responseItem.description,
                    secondaryText,
                    imageLink,
                    responseItem.metadata.interactions.like.added
                )
            )
        }
        return returnList
    }

    // endregion
}