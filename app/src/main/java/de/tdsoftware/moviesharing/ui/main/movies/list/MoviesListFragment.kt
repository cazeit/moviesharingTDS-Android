package de.tdsoftware.moviesharing.ui.main.movies.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

/**
 * Fragment that is part of MoviesFragment's ViewPager, handling the recyclerView in it (list-view of movies)
 */
class MoviesListFragment : MoviesBaseFragment() {

    // region public types
    companion object {

        @JvmStatic
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }

    // endregion

    // region properties
    private lateinit var mainView: MoviesListFragmentView

    private val playlistRecyclerAdapter: PlaylistListAdapter
        get() {
            return playlistAdapter as PlaylistListAdapter
        }

    // endregion

    // region public API
    override fun createPlayListAdapter(): PlaylistBaseAdapter {
        return PlaylistListAdapter()
    }

    // endregion


    // region lifecycle callbacks
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies_list,container,false) as MoviesListFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMainView()
    }
    // endregion

    // region private API
    /**
     * the playlistAdapter gets created in parent (MainActivityBaseFragment) and then is here set to the recyclerView
     */
    private fun setUpMainView() {
        mainView.changePlaylistRecyclerAdapter(playlistRecyclerAdapter)
    }
    // endregion

}