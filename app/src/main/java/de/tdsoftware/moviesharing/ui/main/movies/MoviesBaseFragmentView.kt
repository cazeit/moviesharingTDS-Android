package de.tdsoftware.moviesharing.ui.main.movies

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

/**
 * this is the parent-view class for MoviesGridFrView and MoviesListFrView,
 */
abstract class MoviesBaseFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    protected lateinit var playlistRecyclerView: RecyclerView

    fun changePlaylistRecyclerAdapter(adapter: PlaylistBaseAdapter) {
        playlistRecyclerView.adapter = adapter
    }
}