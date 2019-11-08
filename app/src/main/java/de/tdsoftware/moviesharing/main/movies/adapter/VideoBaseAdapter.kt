package de.tdsoftware.moviesharing.main.movies.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class VideoBaseAdapter(private val videoCount: Int, private val context: Context): RecyclerView.Adapter<VideoBaseAdapter.ViewHolder>() {

    private val videoList = ArrayList<Int>() //TODO: this is later a list of videoApp

    override fun getItemCount(): Int {
        return videoCount
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setOnClickListener{
            val listeningActivity = context as ItemClickListener
            listeningActivity.onRecyclerItemClick(position, holder.imageView)
            //later we pass the id from the object or the object itself.
        }
        // imageview = list[position].imageLink

    }

    class ViewHolder(itemView: View, imageView: ImageView, textView: TextView): RecyclerView.ViewHolder(itemView){
        val imageView = imageView
        val textView = textView
    }

    interface ItemClickListener{
        fun onRecyclerItemClick(position: Int, view: View)
    }
}