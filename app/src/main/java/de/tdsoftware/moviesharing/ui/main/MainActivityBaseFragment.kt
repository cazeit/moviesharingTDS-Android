package de.tdsoftware.moviesharing.ui.main

import android.content.Intent
import android.os.Bundle
import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.data.models.VideoApp
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.ui.video.VideoDetailsActivity
import de.tdsoftware.moviesharing.util.RecyclerUpdateEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class MainActivityBaseFragment: BaseFragment() {

    protected lateinit var playlistAdapter: PlaylistBaseAdapter

    /** does this make sense or is a initFunction in here (then called from child after list was set) better?**/

    protected var playlistListForAdapter :ArrayList<PlaylistApp>
        set(value){
            playlistAdapter.playlistList = value
            playlistAdapter.notifyDataSetChanged()
        }
        get(){
            return playlistAdapter.playlistList
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlistAdapter = createPlayListAdapter()
        initializeRecyclerItemOnClickListener()

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun initializeRecyclerItemOnClickListener(){
        playlistAdapter.clickListener = object: PlaylistBaseAdapter.Listener{
            override fun onMovieSelected(video: VideoApp) {
                val intent = Intent(context, VideoDetailsActivity::class.java)
                intent.putExtra("video", video)
                startActivity(intent)
            }
        }
    }

    @Subscribe
    abstract fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent)


    abstract fun createPlayListAdapter(): PlaylistBaseAdapter

}