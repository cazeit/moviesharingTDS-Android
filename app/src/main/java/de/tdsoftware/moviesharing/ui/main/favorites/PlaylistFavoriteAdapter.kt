package de.tdsoftware.moviesharing.ui.main.favorites

import android.view.View
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.main.adapter.MoviesBaseAdapter
import de.tdsoftware.moviesharing.ui.main.movies.list.PlaylistListAdapter

/**
 * adapter for the recyclerView in FavoriteFragment
 */
class PlaylistFavoriteAdapter : PlaylistListAdapter() {

    private lateinit var movieAdapter: MoviesBaseAdapter

    // public API
    fun notifyMovieChanged(removeIndex: Int) {
        if(removeIndex == -1) {
            movieAdapter.notifyItemInserted(0)
            movieAdapter.notifyItemRangeChanged(0, movieAdapter.movieList.size)
        }else {
            movieAdapter.notifyItemRemoved(removeIndex)
            movieAdapter.notifyItemRangeChanged(removeIndex, movieAdapter.movieList.size)
        }
    }

    fun changeFavoriteMovieList(movieList: ArrayList<Movie>) {
        movieAdapter.movieList = movieList
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