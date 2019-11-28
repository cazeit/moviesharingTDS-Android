package de.tdsoftware.moviesharing.ui.main.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.main.MainActivityBaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.util.MoviesManager
import de.tdsoftware.moviesharing.util.Notification

/**
 * Favorite-Fragment, has a SearchView and a list of all favorites
 */
class FavoritesFragment : MainActivityBaseFragment() {

    // region public types

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }

        private val TAG = FavoritesFragment::class.java.simpleName
    }

    // endregion

    // region properties

    private val favoritePlaylistRecyclerAdapter: PlaylistFavoriteAdapter
        get() {
            return playlistAdapter as PlaylistFavoriteAdapter
        }

    private lateinit var filteredList: ArrayList<Movie>
    private lateinit var fullList: ArrayList<Movie>

    private lateinit var mainView: FavoritesFragmentView

    // endregion

    // region EventBus

    override fun onNotification(notification: Notification) {
        super.onNotification(notification)
        when (notification) {
            is Notification.FavoriteChangedEvent -> {
                Log.v(TAG, "Event fired in FavoriteFragment")

                // update full-list
                fullList = notification.favoriteList

                // filter the list
                filteredList = filter(mainView.searchViewQuery)
                // set the playlist-list in the adapter by using the filteredList
                favoritePlaylistRecyclerAdapter.playlistList = createFilteredPlaylistList(filteredList)
                favoritePlaylistRecyclerAdapter.notifyMovieChanged(notification.removeIndex)
                // check if empty-state-text needs to be displayed
                if (!checkFullListEmptyState()) {
                    checkFilteredListEmptyState()
                }
            }
        }
    }

    // endregion

    // region MainActivityBaseFragment-implementations

    override fun createPlayListAdapter(): PlaylistBaseAdapter {
        return PlaylistFavoriteAdapter()
    }

    // endregion

    // region lifecycle callbacks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView =
            inflater.inflate(R.layout.fragment_favorites, container, false) as FavoritesFragmentView
        return mainView
    }

    /**
     * here we initialize the fullList(list of all favorites) and initialize the playlistList inside the adapter (list of 1 playlist with favorites)
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullList = MoviesManager.favoritePlaylist.movieList
        playlistListInAdapter = MoviesManager.favoritePlaylistList

        setUpMainView()
    }

    /**
     * This makes the SearchView unfocused when entering the fragment, when coming back from MovieDetailsActivity
     */
    override fun onResume() {
        super.onResume()
        mainView.requestFocus()
    }

    // endregion

    // private API

    private fun createFilteredPlaylistList(movieList: ArrayList<Movie>): ArrayList<Playlist> {
        val playlist = Playlist("favorites_filtered", "Favorites", movieList)
        return arrayListOf(playlist)
    }

    /**
     * Note: playlistRecyclerAdapter is being created in parent-class beforehand
     */
    private fun setUpMainView() {
        mainView.changePlaylistRecyclerAdapter(favoritePlaylistRecyclerAdapter)

        mainView.viewListener = object : FavoritesFragmentView.Listener {
            override fun onQueryChange(newText: String) {
                if (!checkFullListEmptyState()) {
                    filteredList = filter(newText)
                    playlistListInAdapter = createFilteredPlaylistList(filteredList)
                    checkFilteredListEmptyState()
                }
            }
        }
        checkFullListEmptyState()
    }

    /**
     * filter method that iterates through full list and returns all movies
     * (filtering by query and title of movie)
     */
    private fun filter(query: String): ArrayList<Movie> {
        return if (query.isEmpty()) {
            fullList
        } else {
            val retList = ArrayList<Movie>()
            for (movie in fullList) {
                if (movie.title.contains(query, true)) {
                    retList.add(movie)
                }
            }
            retList
        }
    }

    /**
     * check if there are filtered results
     */
    private fun checkFilteredListEmptyState() {
        Log.v(TAG, "There are " + filteredList.size + " favorites that match the query!")
        if (filteredList.isEmpty()) {
            mainView.hintText = resources.getString(R.string.empty_search_text_hint)
            mainView.changeEmptyStateTextVisibility(true)
        } else {
            mainView.changeEmptyStateTextVisibility(false)
        }
    }

    /**
     * check if there are favorites at all
     */
    private fun checkFullListEmptyState(): Boolean {
        return if (fullList.isEmpty()) {
            mainView.hintText = resources.getString(R.string.empty_list_text_hint)
            mainView.changeEmptyStateTextVisibility(true)
            true
        } else {
            mainView.changeEmptyStateTextVisibility(false)
            false
        }
    }

    // endregion
}