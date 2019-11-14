package de.tdsoftware.moviesharing.ui.main.movies.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

open class PlaylistListAdapter: PlaylistBaseAdapter() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoRecyclerView.layoutManager =
                LinearLayoutManager(holder.videoRecyclerView.context, RecyclerView.VERTICAL, false)
        holder.videoRecyclerView.adapter =
            VideoListAdapter(playlistList[position].videoList)
        super.onBindViewHolder(holder, position)
    }

}