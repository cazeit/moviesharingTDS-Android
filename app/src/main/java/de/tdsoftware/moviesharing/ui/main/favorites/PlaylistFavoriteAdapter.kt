package de.tdsoftware.moviesharing.ui.main.favorites

import android.view.View
import de.tdsoftware.moviesharing.ui.main.movies.list.PlaylistListAdapter

/**
 * adapter for the recyclerView in FavoriteFragment
 */
class PlaylistFavoriteAdapter : PlaylistListAdapter() {

    // region RecyclerView.Adapter-implementations

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.playlistTitleTextView.visibility = View.GONE
    }

    // enregion
}