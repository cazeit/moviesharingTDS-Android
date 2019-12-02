package de.tdsoftware.moviesharing.ui.main.favorites

import android.os.Bundle
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
            is Notification.FavoritesChangedEvent -> {
                // update full-list
                fullList = notification.favoriteList
                // update filtered-list
                filteredList = filter(mainView.searchViewQuery)
                // change the movieList of the movie-adapter over playlist-adapter-method
                favoritePlaylistRecyclerAdapter.changeFavoriteMovieList(filteredList)
                //notify adapter that data has changed @removedIndex/ data was added (-1)
                favoritePlaylistRecyclerAdapter.notifyMovieAdded()
                // check if empty-state-text needs to be displayed
                if (!checkFullListEmptyState()) {
                    checkFilteredListEmptyState()
                }
            }
            is Notification.FavoritesRemovedEvent -> {
                fullList = notification.favoriteList
                filteredList = filter(mainView.searchViewQuery)
                favoritePlaylistRecyclerAdapter.changeFavoriteMovieList(filteredList)
                favoritePlaylistRecyclerAdapter.notifyMovieRemoved(notification.removedIndex)
                if(!checkFullListEmptyState()) {
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
     * get the full list of favorites from MoviesManager and set-up the recyclerView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullList = MoviesManager.favoritePlaylist.movieList
        playlistListInAdapter = MoviesManager.favoritePlaylistList

        setUpMainView()
    }

    /**
     * request focus on main-view, so SearchView-focus is cleared
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
     * filter method that iterates through full list and returns all movies that match the query in a list
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
     * check if there are filtered results and if not show emptyState in UI
     */
    private fun checkFilteredListEmptyState() {
        if (filteredList.isEmpty()) {
            mainView.hintText = resources.getString(R.string.empty_search_text_hint)
            mainView.changeEmptyStateTextVisibility(true)
        } else {
            mainView.changeEmptyStateTextVisibility(false)
        }
    }

    /**
     * check if there are favorites at all and if not show emptyState in UI
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