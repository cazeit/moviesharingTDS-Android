package de.tdsoftware.moviesharing.main.favorites

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.movies.adapter.VideoListAdapter

class FavoritesFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    // region Public Types

    interface Listener {

    }

    // endregion

    // region Properties

    var viewListener: Listener? = null
    private lateinit var favoriteRecyclerView: RecyclerView
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
        favoriteRecyclerView = findViewById(R.id.fragment_favorites_recycler_view_videos)
        favoriteSearchView = findViewById(R.id.fragment_favorites_search_view)
    }

    private fun setupControls() {

    }

    private fun buildRecyclerView(){
        favoriteRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        favoriteRecyclerView.adapter = VideoListAdapter(4,context)
    }

    // endregion 
}