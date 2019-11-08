package de.tdsoftware.moviesharing.main.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.tdsoftware.moviesharing.R

class VideoListAdapter(videoAmount: Int, context: Context): VideoBaseAdapter(videoAmount, context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_videos_list,parent, false)
        val imageView = rootView.findViewById<ImageView>(R.id.recycler_item_videos_list_image)
        val textView = rootView.findViewById<TextView>(R.id.recycler_item_videos_list_title)
        return ViewHolder(rootView,imageView,textView)
    }
}