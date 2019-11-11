package de.tdsoftware.moviesharing.ui.main.movies

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistBaseAdapter

/**
 * this is the parent-view class for MoviesGridFrView and MoviesListFrView, to change the adapter (really necessary? or twice same function?)
 */
abstract class MoviesViewFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    protected lateinit var playlistRecyclerView: RecyclerView

    fun changePlaylistRecyclerAdapter(adapter: PlaylistBaseAdapter){
        playlistRecyclerView.adapter = adapter
    }

}