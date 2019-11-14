package de.tdsoftware.moviesharing.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie

abstract class PlaylistBaseAdapter:
    RecyclerView.Adapter<PlaylistBaseAdapter.ViewHolder>(){

    var playlistList: ArrayList<Playlist> = ArrayList<Playlist>()

    interface Listener {
        fun onMovieSelected(movie: Movie)
    }

    var clickListener: Listener? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playlistTitle = playlistList[position].title
        holder.movieRecyclerAdapter?.listener = object: VideoBaseAdapter.ItemClickListener{
            override fun onRecyclerItemClick(movie: Movie) {
                clickListener?.onMovieSelected(movie)
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

        val movieRecyclerView: RecyclerView =
                itemView.findViewById(R.id.recycler_item_playlists_recylcer_view_movies)

        var playlistTitle: String?
            get(){
                return playlistTitleTextView.text.toString()
            }
            set(value){
                playlistTitleTextView.text = value
            }

        var movieRecyclerAdapter: VideoBaseAdapter?
            get(){
                return movieRecyclerView.adapter as VideoBaseAdapter?
            }
            set(value){
                movieRecyclerView.adapter = value
            }
    }
}