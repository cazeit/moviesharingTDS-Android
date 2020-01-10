package de.tdsoftware.moviesharing.ui.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.model.Movie
import de.tdsoftware.moviesharing.util.NetworkManager
import jp.wasabeef.picasso.transformations.CropTransformation

/**
 * BaseAdapter for the RecyclerView inside one Playlist-RecyclerView-Item
 */
abstract class MoviesBaseAdapter(var movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesBaseAdapter.ViewHolder>() {

    // Region public types

    class ViewHolder(
        itemView: View,
        val movieThumbnailImageView: ImageView,
        private val movieTitleTextView: TextView,
        val movieThumbnailCardView: CardView
    ) : RecyclerView.ViewHolder(itemView) {
        var movieTitle: String?
            get() {
                return movieTitleTextView.text.toString()
            }
            set(value) {
                movieTitleTextView.text = value
            }
    }

    interface ItemClickListener {
        fun onRecyclerItemClick(movie: Movie, view: View)
    }

    // endregion

    // region properties

    var clickListener: ItemClickListener? = null

    // endregion

    // region RecyclerView.Adapter-implementations

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle = movieList[position].title

        val picasso = Picasso.get().load(movieList[position].imageUrl)
            .placeholder(R.drawable.sample_movie_image)
        if(NetworkManager.sourceApi == NetworkManager.ApiName.YOUTUBE) {
            val transformation = CropTransformation(160,240)
            picasso.transform(transformation)
        }
        picasso.into(holder.movieThumbnailImageView)

        holder.movieThumbnailImageView.setOnClickListener {
            holder.movieThumbnailImageView.isEnabled = false
            clickListener?.onRecyclerItemClick(movieList[position], holder.movieThumbnailCardView)
            holder.movieThumbnailImageView.isEnabled = true
        }
    }

    // endregion


}