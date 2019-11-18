package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.Playlist
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//TODO: initialization of favorites (from sharedPref)
//TODO: get the data from api

object Repository {

    // this causes crash if the app gets killed by OS -> we can init by lazy, which would work with sample data or calling API (but update UI as loading as well)
    // but the simplest method is to just "restart" app by launching LoadingActivity if list is not initialized
    lateinit var playlistList: ArrayList<Playlist>
    lateinit var favoritePlaylistList: ArrayList<Playlist>

    var favoritePlaylist: Playlist
        get() {
            return favoritePlaylistList[0]
        }
        set(value) {
            favoritePlaylistList[0] = value
        }

    init {
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteUpdateEvent(favoriteUpdateEvent: FavoriteUpdateEvent) {
        val movie = favoriteUpdateEvent.movie
        for(currentMovie in favoritePlaylist.movieList) {
            if(currentMovie.id == movie.id) {
                favoritePlaylist.movieList.remove(currentMovie)
                EventBus.getDefault().post(RecyclerUpdateEvent())
                return
            }
        }
        favoritePlaylist.movieList.add(movie)
        EventBus.getDefault().post(RecyclerUpdateEvent())
    }
}