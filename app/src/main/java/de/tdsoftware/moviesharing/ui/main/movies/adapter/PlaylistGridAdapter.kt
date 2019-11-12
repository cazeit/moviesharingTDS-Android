package de.tdsoftware.moviesharing.ui.main.movies.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.data.models.PlaylistApp

class PlaylistGridAdapter(playlistList: ArrayList<PlaylistApp>): PlaylistBaseAdapter(playlistList) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoRecyclerView.layoutManager =
                LinearLayoutManager(holder.videoRecyclerView.context, RecyclerView.HORIZONTAL, false)
        holder.videoRecyclerAdapter = VideoGridAdapter(playlistList[position].videoList)
        super.onBindViewHolder(holder, position)
    }

}