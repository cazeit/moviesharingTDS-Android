package de.tdsoftware.moviesharing.ui.main.movies.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistGridAdapter(clickListener: VideoBaseAdapter.ItemClickListener, private val playlistAmount: Int): PlaylistBaseAdapter(clickListener, playlistAmount) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoRecyclerView.layoutManager = LinearLayoutManager(holder.videoRecyclerView.context, RecyclerView.HORIZONTAL, false)
        holder.videoRecyclerAdapter = VideoGridAdapter(10)
        super.onBindViewHolder(holder, position)
    }

}