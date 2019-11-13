package de.tdsoftware.moviesharing.ui.main.movies

import android.content.Intent
import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.data.models.VideoApp
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.ui.video.VideoDetailsActivity

abstract class MoviesBaseFragment: BaseFragment() {

    protected lateinit var playlistAdapter: PlaylistBaseAdapter

    /** does this make sense or is a initFunction in here (then called from child after list was set) better?**/

    protected var playlistList :ArrayList<PlaylistApp>
        set(value){
            playlistAdapter =  createPlayListAdapter(value)
            playlistAdapter.clickListener = object: PlaylistBaseAdapter.Listener{
                override fun onMovieSelected(video: VideoApp) {
                    val intent = Intent(context, VideoDetailsActivity::class.java)
                    intent.putExtra("video", video)
                    startActivity(intent)
                }
            }
        }
        get(){
            return playlistAdapter.playlistList
        }


    abstract fun createPlayListAdapter(playlistList: ArrayList<PlaylistApp>): PlaylistBaseAdapter

}