package de.tdsoftware.moviesharing.util

import android.content.SharedPreferences
import android.util.Log
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.util.requests.MoviesRequest
import de.tdsoftware.moviesharing.util.requests.Request
import org.greenrobot.eventbus.EventBus

/**
 * Singleton that handles everything around Movies -> API for Movies
 */
object MoviesManager {

    // properties

    private val TAG = MoviesManager::class.java.simpleName
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

    // endregion

    // constructors
    /**
     * creating a playlist for favorites at runtime
     */
    init {
        val favoriteList = Playlist("fav001", "Favorite", ArrayList())
        favoritePlaylistList.add(favoriteList)
    }
    // endregion


    // public API
    fun setUpMoviesManager(sharedPref: SharedPreferences) {
        sharedPreferences = sharedPref
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

    fun fetchPlaylistListWithMovies() {
        NetworkManager.fetchPlaylistList {
            when (it) {
                is Result.Success -> {
                    playlistList.addAll(it.data)
                    Log.v(TAG, "Empty playlist-list is now stored in MoviesManager.")
                    fetchMoviesForPlaylistList()
                }
                is Result.Error -> {
                    EventBus.getDefault().post(Notification.NetworkErrorEvent(it.code, it.message))
                }
            }
        }
    }

    // endregion

    // private API

    private fun fetchMoviesForPlaylistList() {
        for (playlist in playlistList) {
            fetchMoviesForPlaylist(playlist)
        }
    }

    private fun fetchMoviesForPlaylist(playlist: Playlist) {
        NetworkManager.fetchMoviesFromPlaylist(playlist) {
            when (it) {
                is Result.Success -> {
                    Log.v(
                        TAG,
                        "Result for playlist with name: " + playlist.title + ". There are " + it.data.size + " videos in this playlist."
                    )
                    playlist.movieList.addAll(it.data)
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
                    EventBus.getDefault().post(Notification.NetworkErrorEvent(it.code, it.message))
                    // unregister all pending requests when there was an error
                    Request.unregisterAll(MoviesRequest::class.java)
                }
            }
        }
    }

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