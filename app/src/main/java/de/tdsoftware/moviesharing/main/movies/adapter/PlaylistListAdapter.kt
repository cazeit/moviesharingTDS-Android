package de.tdsoftware.moviesharing.main.movies.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistListAdapter(playlistAmount: Int): PlaylistBaseAdapter(playlistAmount) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.reyclerView.layoutManager = LinearLayoutManager(holder.reyclerView.context, RecyclerView.VERTICAL, false)
        holder.reyclerView.adapter = VideoListAdapter(10)
    }
}