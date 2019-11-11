package de.tdsoftware.moviesharing.ui.main.movies.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistListAdapter(clickListener: VideoBaseAdapter.ItemClickListener, playlistAmount: Int): PlaylistBaseAdapter(clickListener, playlistAmount) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoRecyclerView.layoutManager = LinearLayoutManager(holder.videoRecyclerView.context, RecyclerView.VERTICAL, false)
        holder.videoRecyclerView.adapter = VideoListAdapter(10)
        super.onBindViewHolder(holder, position)
    }

}