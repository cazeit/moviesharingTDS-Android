package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.Playlist
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//TODO: initialization of favorites (from sharedPref)
//TODO: get the data from api
object Repository {

    lateinit var playlistList: ArrayList<Playlist>
    lateinit var favoritePlaylistList: ArrayList<Playlist>

    var favoritePlaylist: Playlist
        get(){
            return favoritePlaylistList[0]
        }
        set(value){
            favoritePlaylistList[0] = value
        }

    init{
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteUpdateEvent(favoriteUpdateEvent: FavoriteUpdateEvent){
        val movie = favoriteUpdateEvent.movie
        for(currentVideo in favoritePlaylist.movieList){
            if(currentVideo.id == movie.id){
                favoritePlaylist.movieList.remove(currentVideo)
                EventBus.getDefault().post(RecyclerUpdateEvent())
                return
            }
        }
        favoritePlaylist.movieList.add(movie)
        EventBus.getDefault().post(RecyclerUpdateEvent())
    }
}