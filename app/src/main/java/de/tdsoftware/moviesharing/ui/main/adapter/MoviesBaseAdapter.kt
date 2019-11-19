package de.tdsoftware.moviesharing.ui.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.tdsoftware.moviesharing.data.models.Movie
import jp.wasabeef.picasso.transformations.CropTransformation

/**
 * BaseAdapter for the RecyclerView inside one item of the Playlist-RecyclerView
 */
abstract class MoviesBaseAdapter(private var movieList: ArrayList<Movie>): RecyclerView.Adapter<MoviesBaseAdapter.ViewHolder>() {

    // Region public types
    class ViewHolder(itemView: View,
                     val movieThumbnailImageView: ImageView,
                     private val movieTitleTextView: TextView): RecyclerView.ViewHolder(itemView) {
        var movieTitle: String?
            get() {
                return movieTitleTextView.text.toString()
            }
            set(value) {
                movieTitleTextView.text = value
            }
    }

    interface ItemClickListener {
        fun onRecyclerItemClick(movie: Movie)
    }

    // endregion

    // region properties

    var listener: ItemClickListener? = null

    // endregion

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle = movieList[position].title

        val transformation = CropTransformation(160, 240)
        Picasso.get().load(movieList[position].imagePath).transform(transformation)
            .into(holder.movieThumbnailImageView)

        holder.movieThumbnailImageView.setOnClickListener {
            listener?.onRecyclerItemClick(movieList[position])
        }
    }


}