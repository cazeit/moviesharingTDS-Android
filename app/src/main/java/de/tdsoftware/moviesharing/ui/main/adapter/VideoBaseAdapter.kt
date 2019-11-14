package de.tdsoftware.moviesharing.ui.main.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.data.models.Movie

abstract class VideoBaseAdapter(private var movieList: ArrayList<Movie>): RecyclerView.Adapter<VideoBaseAdapter.ViewHolder>() {

    interface ItemClickListener{
        fun onRecyclerItemClick(movie: Movie)
    }

    var listener: ItemClickListener? = null

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle = movieList[position].title
        //holder.movieThumbnail = movieList[position].imagePath.toUri()

        holder.movieThumbnailImageView.setOnClickListener{
            listener?.onRecyclerItemClick(movieList[position])
        }
    }

    class ViewHolder(itemView: View,
                     val movieThumbnailImageView: ImageView,
                     private val movieTitleTextView: TextView): RecyclerView.ViewHolder(itemView){
        var movieTitle: String?
            get(){
                return movieTitleTextView.text.toString()
            }
            set(value){
                movieTitleTextView.text = value
            }
        var movieThumbnail: Uri?
            set(value){
                movieThumbnailImageView.setImageURI(value)
            }
            get(){
                /** i cannot get uri of an imageview, but will never need the getter, is it okay to implement a wrong getter then?**/
                return "".toUri()
            }
    }
}