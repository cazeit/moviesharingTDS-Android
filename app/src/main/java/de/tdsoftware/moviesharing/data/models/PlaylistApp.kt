package de.tdsoftware.moviesharing.data.models

class PlaylistApp(val id: String, val title: String?, var videoList: ArrayList<VideoApp>) {

    fun changeVideoList(videoListNew: ArrayList<VideoApp>){
        videoList = videoListNew
    }
}