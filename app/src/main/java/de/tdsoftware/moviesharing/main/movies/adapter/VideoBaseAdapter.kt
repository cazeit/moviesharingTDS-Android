package de.tdsoftware.moviesharing.main.movies.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class VideoBaseAdapter(private val videoCount: Int): RecyclerView.Adapter<VideoBaseAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return videoCount
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO(Here we bind from lateron list)
    }

    class ViewHolder(itemView: View, imageView: ImageView, textView: TextView): RecyclerView.ViewHolder(itemView){
        val imageView = imageView
        val textView = textView
    }
}