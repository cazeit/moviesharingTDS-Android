package de.tdsoftware.moviesharing.ui.main.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.main.adapter.MoviesBaseAdapter

/**
 * adapter for recyclerView inside recyclerView's ViewHolder from MoviesListFragment
 */
class MoviesListAdapter(movieList: ArrayList<Movie>) : MoviesBaseAdapter(movieList) {

    // region RecyclerView.Adapter-implementations

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_movies_list, parent, false)
        val imageView = rootView.findViewById<ImageView>(R.id.recycler_item_movies_list_thumbnail_image_view)
        val textView = rootView.findViewById<TextView>(R.id.recycler_item_movies_list_title_text_view)
        val cardView = rootView.findViewById<CardView>(R.id.recycler_item_movies_list_card_view)
        return ViewHolder(
            rootView,
            imageView,
            textView,
            cardView
        )
    }

    // endregion
}