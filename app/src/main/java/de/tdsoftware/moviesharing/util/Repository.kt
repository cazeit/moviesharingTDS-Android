package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.data.models.VideoApp
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

object Repository {

    lateinit var playlistList: ArrayList<PlaylistApp>
    lateinit var favoritePlaylistList: ArrayList<PlaylistApp>

    init{
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVideoUpdateEvent(videoUpdateEvent: VideoUpdateEvent){
        for(playlist in playlistList){
            for(video in playlist.videoList){
                if(video.id == videoUpdateEvent.video.id){
                    if(video.isFavorite != videoUpdateEvent.video.isFavorite) {
                        video.isFavorite = videoUpdateEvent.video.isFavorite
                        updateFavorites(video)
                    }
                    video.rating = videoUpdateEvent.video.rating
                    break
                }
                EventBus.getDefault().post(RecyclerUpdateEvent())
            }
        }
    }


    private fun updateFavorites(video: VideoApp){
        for(currentVideo in favoritePlaylistList[0].videoList){
            if(currentVideo.id == video.id){
                favoritePlaylistList[0].videoList.remove(currentVideo)
                return
            }
        }
        favoritePlaylistList[0].videoList.add(video)
    }
}