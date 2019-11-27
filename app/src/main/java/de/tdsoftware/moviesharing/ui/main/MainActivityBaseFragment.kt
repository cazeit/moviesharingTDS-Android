package de.tdsoftware.moviesharing.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.ui.moviedetails.MovieDetailsActivity
import de.tdsoftware.moviesharing.ui.moviedetails.MovieDetailsFragmentView.Companion.CARD_BANNER
import de.tdsoftware.moviesharing.ui.moviedetails.MovieDetailsFragmentView.Companion.CARD_COVER

/**
 * BaseFragment for all Fragments in MainActivity
 */
abstract class MainActivityBaseFragment : BaseFragment() {

    // region properties
    /**
     * Adapter for RecyclerView that each Fragment has
     */
    protected lateinit var playlistAdapter: PlaylistBaseAdapter

    /**
     * List inside RecyclerAdapter above, by setting, the RecyclerView updates
     */
    protected var playlistListInAdapter: ArrayList<Playlist>
        set(value) {
            playlistAdapter.playlistList = value
            playlistAdapter.notifyDataSetChanged()
        }
        get() {
            return playlistAdapter.playlistList
        }

    private val logTag = this::class.java.simpleName

    // endregion

    // region public API

    abstract fun createPlayListAdapter(): PlaylistBaseAdapter

    // endregion

    // region lifecycle callbacks

    /**
     * create a playlist-adapter (empty list) and initialize the clickListener (@see PlaylistBaseAdapter)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlistAdapter = createPlayListAdapter()
        initializeRecyclerItemOnClickListener()
    }

    // endregion

    // region private API

    private fun initializeRecyclerItemOnClickListener() {

        playlistAdapter.clickListener = object : PlaylistBaseAdapter.Listener {
            override fun onMovieSelected(movie: Movie, view: View) {
                Log.v(logTag, "Movie selected with title: " + movie.title)

                val intent = Intent(context, MovieDetailsActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivityBaseFragment.requireActivity(), Pair(view, CARD_BANNER), Pair(view, CARD_COVER))
                intent.putExtra("movie", movie)
                startActivity(intent, options.toBundle())
            }
        }
    }

    // endregion

}