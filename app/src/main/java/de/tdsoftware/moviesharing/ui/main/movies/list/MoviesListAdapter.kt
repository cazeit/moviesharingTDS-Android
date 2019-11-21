package de.tdsoftware.moviesharing.ui.main.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.main.adapter.MoviesBaseAdapter

/**
 * adapter for recyclerView inside recyclerView's ViewHolder from MoviesListFragment
 */
class MoviesListAdapter(movieList: ArrayList<Movie>): MoviesBaseAdapter(movieList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_movies_list,parent, false)
        val imageView = rootView.findViewById<ImageView>(R.id.recycler_item_movies_list_image)
        val textView = rootView.findViewById<TextView>(R.id.recycler_item_movies_list_title)
        return ViewHolder(
            rootView,
            imageView,
            textView
        )
    }
}