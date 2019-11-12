package de.tdsoftware.moviesharing.ui.main.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.data.models.VideoApp

abstract class PlaylistBaseAdapter(var playlistList: ArrayList<PlaylistApp>):
    RecyclerView.Adapter<PlaylistBaseAdapter.ViewHolder>(){

    interface Listener {
        fun onMovieSelected(video: VideoApp)
    }

    var clickListener: Listener? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playlistTitle = playlistList[position].title
        holder.videoRecyclerAdapter?.listener = object: VideoBaseAdapter.ItemClickListener{
            override fun onRecyclerItemClick(video: VideoApp) {
                clickListener?.onMovieSelected(video)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_playlists, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val playlistTitleTextView =
                itemView.findViewById<TextView>(R.id.recycler_item_playlists_title)

        val videoRecyclerView: RecyclerView =
                itemView.findViewById(R.id.recycler_item_playlists_recylcer_view_videos)

        var playlistTitle: String?
            get(){
                return playlistTitleTextView.text.toString()
            }
            set(value){
                playlistTitleTextView.text = value
            }

        var videoRecyclerAdapter: VideoBaseAdapter?
            get(){
                return videoRecyclerView.adapter as VideoBaseAdapter?
            }
            set(value){
                videoRecyclerView.adapter = value
            }
    }
}