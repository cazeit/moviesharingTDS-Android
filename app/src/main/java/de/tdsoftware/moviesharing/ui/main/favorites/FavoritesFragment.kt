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
import de.tdsoftware.moviesharing.util.RecyclerUpdateEvent
import de.tdsoftware.moviesharing.util.Repository
import org.greenrobot.eventbus.Subscribe

/**
 * Favorite-Fragment, has a SearchView and a list of all favorites
 *
 * Important to note: The structure of a recyclerView containing other recyclerViews inside each item stays,
 * as we pass a list containing only one runtime-generated playlist-object, that contains a list of all favorites
 */
class FavoritesFragment: MainActivityBaseFragment() {

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

    // region lifecycle callbacks
    override fun createPlayListAdapter(): PlaylistBaseAdapter {
        return PlaylistFavoriteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_favorites, container, false) as FavoritesFragmentView
        return mainView
    }

    /**
     * here we initialize the fullList(list of all favorites) and initialize the playlistList inside the adapter (list of 1 playlist with favorites)
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullList = Repository.favoritePlaylist.movieList
        playlistListInAdapter = Repository.favoritePlaylistList

        setUpMainView()
    }

    @Subscribe
    override fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent) {
        // update full-list
        fullList = Repository.favoritePlaylist.movieList

        Log.v(TAG, "There are " + fullList.size + " favorites in total!")
        // filter the list
        filteredList = filter(mainView.searchViewQuery)
        // set the playlist-list in the adapter by using the filteredList
        playlistListInAdapter = createFilteredPlaylistList(filteredList)
        // check if empty-state-text needs to be displayed
        if(!checkFullListEmptyState()) {
            checkFilteredListEmptyState()
        }
    }
    // endregion

    // private API

    private fun createFilteredPlaylistList(movieList: ArrayList<Movie>): ArrayList<Playlist> {
        val playlist = Playlist("favorites_filtered","Favorites", movieList)
        return arrayListOf(playlist)
    }

    /**
     *  the playlistAdapter gets created in parent (MainActivityBaseFragment) and then is here set to the recyclerView

     */
    private fun setUpMainView() {
        mainView.changePlaylistRecyclerAdapter(favoritePlaylistRecyclerAdapter)

        mainView.viewListener = object : FavoritesFragmentView.Listener {
            override fun onQueryChange(newText: String) {
                if(!checkFullListEmptyState()) {
                    filteredList = filter(newText)
                    playlistListInAdapter = createFilteredPlaylistList(filteredList)
                    checkFilteredListEmptyState()
                }
            }
        }
        checkFullListEmptyState()
    }

    /**
     * filter method that iterates through full list and returns all movies, the title of which contains query
     */
    private fun filter(query: String): ArrayList<Movie> {
        return if(query.isEmpty()) {
            fullList
        }else {
            val retList = ArrayList<Movie>()
            for(movie in fullList) {
                if(movie.title.contains(query, true)) {
                    retList.add(movie)
                }
            }
            retList
        }
    }

    /**
     * check if filteredList is empty and if so display a hint
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
     * check if fullList is empty and if so display a hint
     */
    private fun checkFullListEmptyState(): Boolean {
        return if(fullList.isEmpty()) {
            mainView.hintText = resources.getString(R.string.empty_list_text_hint)
            mainView.changeEmptyStateTextVisibility(true)
            true
        }else{
            mainView.changeEmptyStateTextVisibility(false)
            false
        }
    }

    // endregion
}