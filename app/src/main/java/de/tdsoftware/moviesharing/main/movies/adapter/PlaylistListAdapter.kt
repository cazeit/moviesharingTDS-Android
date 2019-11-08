package de.tdsoftware.moviesharing.main.movies.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistListAdapter(playlistAmount: Int, private val context: Context): PlaylistBaseAdapter(playlistAmount) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.reyclerView.layoutManager = LinearLayoutManager(holder.reyclerView.context, RecyclerView.VERTICAL, false)
        holder.reyclerView.adapter = VideoListAdapter(10, context)
    }

}