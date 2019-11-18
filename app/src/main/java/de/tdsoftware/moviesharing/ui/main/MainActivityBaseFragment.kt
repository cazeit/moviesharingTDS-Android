package de.tdsoftware.moviesharing.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.ui.moviedetails.MovieDetailsActivity
import de.tdsoftware.moviesharing.util.RecyclerUpdateEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * BaseFragment for all Fragments in MainActivity
 */
abstract class MainActivityBaseFragment: BaseFragment() {

    // region properties´
    /**
     * Adapter for RecyclerView that each Fragment has
     */
    protected lateinit var playlistAdapter: PlaylistBaseAdapter

    /**
     * List inside RecyclerAdapter above, by setting, the RecyclerView updates
     */
    protected var playlistListInAdapter :ArrayList<Playlist>
        set(value) {
            playlistAdapter.playlistList = value
            playlistAdapter.notifyDataSetChanged()
        }
        get() {
            return playlistAdapter.playlistList
        }

    private val TAG = this::class.java.simpleName

    // endregion

    // region public API

    abstract fun createPlayListAdapter(): PlaylistBaseAdapter

    @Subscribe(threadMode = ThreadMode.MAIN)
    abstract fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent)

    // endregion

    // region lifecycle callbacks

    /**
     * create a playlist-adapter (empty list) and initialize the clickListener (@see PlaylistBaseAdapter)
     */
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

    // endregion

    // region private API

    private fun initializeRecyclerItemOnClickListener() {

        playlistAdapter.clickListener = object: PlaylistBaseAdapter.Listener {
            override fun onMovieSelected(movie: Movie) {
                Log.v(TAG, "Movie selected with title: " + movie.title)
                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movie)
                startActivity(intent)
            }
        }
    }

    // endregion

}