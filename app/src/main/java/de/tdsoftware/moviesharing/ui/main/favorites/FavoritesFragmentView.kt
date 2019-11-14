package de.tdsoftware.moviesharing.ui.main.favorites

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragmentView

class FavoritesFragmentView(context: Context, attrs: AttributeSet?) :
    MoviesBaseFragmentView(context, attrs) {
    // region Public Types

    interface Listener {
        fun onQueryChange(newText: String)
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null
    private lateinit var favoriteSearchView: SearchView
    // endregion

    // region View Lifecycle
    override fun onFinishInflate() {
        super.onFinishInflate()
        postLayoutInitialize()
    }

    // endregion

    // region Private API

    private fun postLayoutInitialize() {
        bindViews()
        setupControls()
        buildRecyclerView()
    }

    private fun bindViews() {
        playlistRecyclerView = findViewById(R.id.fragment_favorites_recycler_view_favorite_playlist)
        favoriteSearchView = findViewById(R.id.fragment_favorites_search_view)
    }

    private fun setupControls() {
        favoriteSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null) {
                    viewListener?.onQueryChange(newText)
                }
                return true
            }
        })
    }

    private fun buildRecyclerView() {
        playlistRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    // endregion 
}