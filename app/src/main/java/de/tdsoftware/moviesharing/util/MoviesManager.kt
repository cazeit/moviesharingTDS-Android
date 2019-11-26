package de.tdsoftware.moviesharing.util

import android.content.SharedPreferences
import android.util.Log
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import org.greenrobot.eventbus.EventBus

/**
 * Singleton that handles everything about Movies & Playlists -> API for Movies
 */
object MoviesManager {

    // region properties

    val playlistList by lazy {
        ArrayList<Playlist>()
    }
    val favoritePlaylistList by lazy {
        ArrayList<Playlist>()
    }

    var favoritePlaylist: Playlist
        get() {
            return favoritePlaylistList[0]
        }
        set(value) {
            favoritePlaylistList[0] = value
        }

    private lateinit var sharedPreferences: SharedPreferences
    private val TAG = MoviesManager::class.java.simpleName

    // endregion

    // constructors

    init {
        val favoriteList = Playlist("fav001", "Favorite", ArrayList())
        favoritePlaylistList.add(favoriteList)
    }
    // endregion


    // public API

    fun setUpMoviesManager(sharedPref: SharedPreferences) {
        sharedPreferences = sharedPref
    }

    fun fetchPlaylistListWithMovies() {
        NetworkManager.fetchPlaylistList { playlistListResult ->
            when (playlistListResult) {
                is Result.Success -> {
                    playlistList.addAll(playlistListResult.data)
                    fetchMoviesForPlaylistList()
                }
                is Result.Error -> {
                    EventBus.getDefault().post(Notification.NetworkErrorEvent(playlistListResult.code, playlistListResult.message))
                }
            }
        }
    }

    fun updateFavorites(movie: Movie) {
        for (currentMovie in favoritePlaylist.movieList) {
            if (currentMovie.id == movie.id) {
                favoritePlaylist.movieList.remove(currentMovie)
                EventBus.getDefault().post(Notification.PlaylistChangedEvent(playlistList))
                EventBus.getDefault()
                    .post(Notification.FavoriteChangedEvent(favoritePlaylist.movieList))
                return
            }
        }
        favoritePlaylist.movieList.add(movie)
        EventBus.getDefault().post(Notification.PlaylistChangedEvent(playlistList))
        EventBus.getDefault().post(Notification.FavoriteChangedEvent(favoritePlaylist.movieList))
    }

    // endregion

    // private API

    private fun fetchMoviesForPlaylistList() {
        for (playlist in playlistList) {
            fetchMoviesForPlaylist(playlist)
        }
    }

    private fun fetchMoviesForPlaylist(playlist: Playlist) {
        NetworkManager.fetchMoviesFromPlaylist(playlist) { movieListResult ->
            when (movieListResult) {
                is Result.Success -> {
                    Log.v(
                        TAG,
                        "Result for playlist with name: " + playlist.title + ". There are " + movieListResult.data.size + " videos in this playlist."
                    )
                    playlist.movieList.addAll(movieListResult.data)
                    if (playlist.id == playlistList.last().id) {
                        initializeFavorites()
                        EventBus.getDefault().post(
                            Notification.PlaylistChangedEvent(
                                playlistList
                            )
                        )
                    }
                }
                is Result.Error -> {
                    EventBus.getDefault().post(Notification.NetworkErrorEvent(movieListResult.code, movieListResult.message))
                }
            }
        }
    }

    /**
     * Method that iterates all playlists and finds favorite videos based on shared-preferences and stores them in favoritePlaylist
     */
    private fun initializeFavorites() {
        for (playlist in playlistList) {
            for (movie in playlist.movieList) {
                if (sharedPreferences.getBoolean(movie.id + "_favorite", false)) {
                    favoritePlaylist.movieList.add(movie)
                }
            }
        }
    }
    // endregion
}