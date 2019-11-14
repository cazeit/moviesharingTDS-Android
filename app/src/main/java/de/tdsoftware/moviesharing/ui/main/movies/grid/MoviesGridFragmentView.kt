package de.tdsoftware.moviesharing.ui.main.movies.grid

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragmentView

class MoviesGridFragmentView(context: Context, attrs: AttributeSet?) :
    MoviesBaseFragmentView(context, attrs) {

    // region Public Types
    interface Listener {
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null
    // endregion

    // region Constructors

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
        buildPlaylistRecycler()
    }

    private fun bindViews() {
        playlistRecyclerView = findViewById(R.id.fragment_movies_recycler_view_playlists_grid)
    }

    private fun setupControls() {

    }

    private fun buildPlaylistRecycler(){
        playlistRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    // endregion 
}