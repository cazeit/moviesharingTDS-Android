package de.tdsoftware.moviesharing.main.movies.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaylistGridAdapter(playlistAmount: Int, private val context: Context): PlaylistBaseAdapter(playlistAmount) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.reyclerView.layoutManager = LinearLayoutManager(holder.reyclerView.context, RecyclerView.HORIZONTAL, false)
        holder.reyclerView.adapter = VideoGridAdapter(10, context)
    }

}