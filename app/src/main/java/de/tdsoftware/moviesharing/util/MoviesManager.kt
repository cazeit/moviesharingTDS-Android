package de.tdsoftware.moviesharing.util

import android.content.SharedPreferences
import de.tdsoftware.moviesharing.data.models.Playlist
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.coroutines.CoroutineContext

//TODO: completion (callback) hier rein, die dannt per eventbus verständigt. im Event ist die Liste enthalten
// BaseClass für die Events (Notification) und die BaseActivity kriegt eine Notification und checkt dann mit when()
// -> onNotificationEvent z.B. ->
//TODO: Output in Result umbenennen, Error hat code und message, die ich am besten selber setze somehow
//TODO: also alles was async / coroutine an sich angeht in den NetworkManager rein (suspend function) -> callback aus MovieManager



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
        EventBus.getDefault().register(this)
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
                is Output.Success<ArrayList<Playlist>> -> {
                    playlistList = it.data
                    initializeFavorites()
                    EventBus.getDefault().post(NetworkSuccessEvent())
                }
                is Output.Error -> {
                    EventBus.getDefault().post(NetworkErrorEvent(it.exception))
                }
            }
        }
    }

    // private API

    // EventBus

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteUpdateEvent(favoriteUpdateEvent: FavoriteUpdateEvent) {
        val movie = favoriteUpdateEvent.movie
        for (currentMovie in favoritePlaylist.movieList) {
            if (currentMovie.id == movie.id) {
                favoritePlaylist.movieList.remove(currentMovie)
                EventBus.getDefault().post(RecyclerUpdateEvent())
                return
            }
        }
        favoritePlaylist.movieList.add(movie)
        EventBus.getDefault().post(RecyclerUpdateEvent())
    }

    // endregion

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