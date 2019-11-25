package de.tdsoftware.moviesharing.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie

/**
 * BaseAdapter for RecyclerView, where each one item represents one playlist
 */
abstract class PlaylistBaseAdapter:
    RecyclerView.Adapter<PlaylistBaseAdapter.ViewHolder>() {

    // region public types

    /**
     * Listener defined to handle onClick inside (@see VideoBaseAdapter)
     */
    interface Listener {
        fun onMovieSelected(movie: Movie)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val playlistTitleTextView: TextView =
            itemView.findViewById(R.id.recycler_item_playlist_title)

        val movieRecyclerView: RecyclerView =
            itemView.findViewById(R.id.recycler_item_playlist_recycler_view_movies)

        var playlistTitle: String?
            get() {
                return playlistTitleTextView.text.toString()
            }
            set(value) {
                playlistTitleTextView.text = value
            }

        var moviesRecyclerAdapter: MoviesBaseAdapter?
            get() {
                return movieRecyclerView.adapter as MoviesBaseAdapter?
            }
            set(value) {
                movieRecyclerView.adapter = value
            }
    }

    // endregion

    // region properties

    var clickListener: Listener? = null

    var playlistList: ArrayList<Playlist> = ArrayList()

    // endregion

    //region lifecycle callbacks

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playlistTitle = playlistList[position].title
        holder.moviesRecyclerAdapter?.listener = object: MoviesBaseAdapter.ItemClickListener {
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

    // endregion
}