package de.tdsoftware.moviesharing.ui.main.movies.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class VideoBaseAdapter(private val videoCount: Int): RecyclerView.Adapter<VideoBaseAdapter.ViewHolder>() {

    var listener: ItemClickListener? = null

    override fun getItemCount(): Int {
        return videoCount
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoThumbnailImageView.setOnClickListener{
            listener?.onRecyclerItemClick(position, holder.videoThumbnailImageView)
            //later we pass the id from the object or the object itself.
        }
        // imageview = list[position].imageLink
    }

    class ViewHolder(itemView: View,
                     val videoThumbnailImageView: ImageView,
                     private val videoTitleTextView: TextView): RecyclerView.ViewHolder(itemView){
        var videoTitle: String?
            get(){
                return videoTitleTextView.text.toString()
            }
            set(value){
                videoTitleTextView.text = value
            }
    }

    interface ItemClickListener{
        fun onRecyclerItemClick(position: Int, imageView: ImageView)
    }
}