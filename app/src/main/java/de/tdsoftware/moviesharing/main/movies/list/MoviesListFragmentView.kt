package de.tdsoftware.moviesharing.main.movies.list

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.movies.MoviesViewFragmentView
import de.tdsoftware.moviesharing.main.movies.adapter.PlaylistListAdapter

class MoviesListFragmentView(context: Context, attrs: AttributeSet?) :
    MoviesViewFragmentView(context, attrs) {
    // region Public Types

    private lateinit var playlistRecyclerView: RecyclerView

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
        playlistRecyclerView = findViewById(R.id.fragment_movies_recycler_view_playlists_list)
    }

    private fun setupControls() {

    }

    private fun buildPlaylistRecycler(){
        playlistRecyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        playlistRecyclerView.adapter = PlaylistListAdapter(2, context)
    }
    // endregion 
}