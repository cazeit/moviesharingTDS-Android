package de.tdsoftware.moviesharing.ui.main.movies.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

/**
 * Fragment that is part of MoviesFragment's ViewPager, handling the recyclerView in it (grid-view of movies)
 */
class MoviesGridFragment : MoviesBaseFragment() {

    // region public types
    companion object {

        @JvmStatic
        fun newInstance(): MoviesGridFragment {
            return MoviesGridFragment()
        }
    }

    // endregion

    // region properties
    private lateinit var mainView: MoviesGridFragmentView

    private val playlistRecyclerAdapter: PlaylistGridAdapter
        get() {
            return playlistAdapter as PlaylistGridAdapter
        }

    // endregion

    // region public API
    override fun createPlayListAdapter(): PlaylistBaseAdapter {
        return PlaylistGridAdapter()
    }

    // endregion

    // region lifecycle callbacks
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(
            R.layout.fragment_movies_grid,
            container,
            false
        ) as MoviesGridFragmentView
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