package de.tdsoftware.moviesharing.ui.main.movies.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.main.adapter.MovieBaseAdapter

class MovieGridAdapter(movieList: ArrayList<Movie>): MovieBaseAdapter(movieList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_movies_grid,parent,false)
        val imageView = rootView.findViewById<ImageView>(R.id.recycler_item_movies_grid_card_image)
        val textView = rootView.findViewById<TextView>(R.id.recycler_item_movies_grid_title)
        return ViewHolder(
            rootView,
            imageView,
            textView
        )
    }
}