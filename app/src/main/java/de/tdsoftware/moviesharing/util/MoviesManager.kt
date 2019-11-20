package de.tdsoftware.moviesharing.util

import android.content.SharedPreferences
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import org.greenrobot.eventbus.EventBus


object MoviesManager {

    // properties

    lateinit var playlistList: ArrayList<Playlist>
    val favoritePlaylistList = ArrayList<Playlist>()

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
    init {
        val favoriteList = Playlist("fav001", "Favorite", ArrayList())
        favoritePlaylistList.add(favoriteList)
    }
    // endregion


    // public API
    fun setUpMoviesManager(sharedPref: SharedPreferences) {
        sharedPreferences = sharedPref
    }

    fun fetchPlaylistList() {
            NetworkManager.fetchAll {
            when (it) {
                is Result.Success<ArrayList<Playlist>> -> {
                    playlistList = it.data
                    initializeFavorites()
                    val playlistChangedEvent = Notification.PlaylistChangedEvent(playlistList)
                    EventBus.getDefault().post(playlistChangedEvent)
                }
                is Result.Error -> {
                    EventBus.getDefault().post(Notification.NetworkErrorEvent(it.code, it.message))
                }
            }
        }
    }

    fun updateFavorites(movie: Movie) {
        for (currentMovie in favoritePlaylist.movieList) {
            if (currentMovie.id == movie.id) {
                favoritePlaylist.movieList.remove(currentMovie)
                EventBus.getDefault().post(Notification.PlaylistChangedEvent(playlistList))
                EventBus.getDefault().post(Notification.FavoriteChangedEvent(favoritePlaylist.movieList))
                return
            }
        }
        favoritePlaylist.movieList.add(movie)
        EventBus.getDefault().post(Notification.PlaylistChangedEvent(playlistList))
        EventBus.getDefault().post(Notification.FavoriteChangedEvent(favoritePlaylist.movieList))
    }

    // private API

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