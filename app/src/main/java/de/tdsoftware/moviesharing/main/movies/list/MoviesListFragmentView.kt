package de.tdsoftware.moviesharing.main.movies.list

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.movies.MoviesViewFragmentView
import de.tdsoftware.moviesharing.main.movies.adapter.PlaylistListAdapter
import de.tdsoftware.moviesharing.main.movies.adapter.VideoListAdapter

class MoviesListFragmentView(context: Context, attrs: AttributeSet?) :
    MoviesViewFragmentView(context, attrs) {
    // region Public Types

    private lateinit var recyclerViewPlaylists: RecyclerView

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
    }

    private fun bindViews() {
        recyclerViewPlaylists = findViewById(R.id.fragment_movies_recycler_view_playlists_list)
        recyclerViewPlaylists.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recyclerViewPlaylists.adapter = PlaylistListAdapter(10)
    }

    private fun setupControls() {

    }

    // endregion 
}