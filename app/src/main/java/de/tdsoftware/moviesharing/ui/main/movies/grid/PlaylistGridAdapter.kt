package de.tdsoftware.moviesharing.ui.main.movies.grid

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

class PlaylistGridAdapter: PlaylistBaseAdapter() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieRecyclerView.layoutManager =
                LinearLayoutManager(holder.movieRecyclerView.context, RecyclerView.HORIZONTAL, false)
        holder.movieRecyclerAdapter =
            MovieGridAdapter(playlistList[position].movieList)
        super.onBindViewHolder(holder, position)
    }

}