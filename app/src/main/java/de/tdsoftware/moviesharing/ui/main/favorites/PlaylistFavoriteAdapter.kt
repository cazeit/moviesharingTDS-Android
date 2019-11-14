package de.tdsoftware.moviesharing.ui.main.favorites

import android.view.View
import de.tdsoftware.moviesharing.ui.main.movies.list.PlaylistListAdapter

/**
 * this way i set the title to not be there. other solution would be to pass a hasTitle parameter to adapter?
 */

class PlaylistFavoriteAdapter: PlaylistListAdapter() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.playlistTitleTextView.visibility = View.GONE
    }
}