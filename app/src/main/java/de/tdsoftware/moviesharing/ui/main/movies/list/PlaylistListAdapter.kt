package de.tdsoftware.moviesharing.ui.main.movies.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

open class PlaylistListAdapter: PlaylistBaseAdapter() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieRecyclerView.layoutManager =
                LinearLayoutManager(holder.movieRecyclerView.context, RecyclerView.VERTICAL, false)
        holder.movieRecyclerView.adapter =
            VideoListAdapter(playlistList[position].movieList)
        super.onBindViewHolder(holder, position)
    }

}