package de.tdsoftware.moviesharing.main.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R

abstract class PlaylistBaseAdapter(private val playlistAmount: Int): RecyclerView.Adapter<PlaylistBaseAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playlistTitle.text = "This is a sample text"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_playlists, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlistAmount
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val playlistTitle = itemView.findViewById<TextView>(R.id.recycler_item_playlists_title)
        val reyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_item_playlists_recylcer_view_videos)
    }
}