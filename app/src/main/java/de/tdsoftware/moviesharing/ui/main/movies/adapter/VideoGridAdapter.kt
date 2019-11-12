package de.tdsoftware.moviesharing.ui.main.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.VideoApp

class VideoGridAdapter(videoList: ArrayList<VideoApp>): VideoBaseAdapter(videoList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_videos_grid,parent,false)
        val imageView = rootView.findViewById<ImageView>(R.id.recycler_item_videos_grid_card_image)
        val textView = rootView.findViewById<TextView>(R.id.recycler_item_videos_grid_title)
        return ViewHolder(rootView, imageView, textView)
    }
}