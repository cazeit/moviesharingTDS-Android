package de.tdsoftware.moviesharing.ui.main.favorites

import android.view.View
import de.tdsoftware.moviesharing.ui.main.adapter.MoviesBaseAdapter
import de.tdsoftware.moviesharing.ui.main.movies.list.PlaylistListAdapter

/**
 * adapter for the recyclerView in FavoriteFragment
 */
class PlaylistFavoriteAdapter : PlaylistListAdapter() {

    private lateinit var movieAdapter: MoviesBaseAdapter

    // public API
    fun notifyMovieChanged(removeIndex: Int){
        if(removeIndex == -1){
            movieAdapter.notifyItemInserted(0)
        }else {
            movieAdapter.notifyItemRemoved(removeIndex)
            movieAdapter.notifyItemRangeChanged(removeIndex, movieAdapter.movieList.size)
            // remove the click listener to prevent crash
        }
    }

    // endregion

    // region RecyclerView.Adapter-implementations

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        movieAdapter = holder.moviesRecyclerAdapter
        holder.playlistTitleTextView.visibility = View.GONE
    }
    // endregion
}