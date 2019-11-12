package de.tdsoftware.moviesharing.ui.main.movies.adapter

import android.view.View
import de.tdsoftware.moviesharing.data.models.PlaylistApp

/**
 * this way i set the title to not be there. other solution would be to pass a hasTitle parameter to adapter?
 */

class PlaylistFavoriteAdapter(playlistList: ArrayList<PlaylistApp>): PlaylistBaseAdapter(playlistList) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.playlistTitleTextView.visibility = View.GONE
    }
}