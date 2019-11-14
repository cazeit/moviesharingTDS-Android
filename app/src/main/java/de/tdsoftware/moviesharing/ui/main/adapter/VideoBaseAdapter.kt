package de.tdsoftware.moviesharing.ui.main.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.data.models.VideoApp

abstract class VideoBaseAdapter(private var videoList: ArrayList<VideoApp>): RecyclerView.Adapter<VideoBaseAdapter.ViewHolder>() {

    interface ItemClickListener{
        fun onRecyclerItemClick(video: VideoApp)
    }

    var listener: ItemClickListener? = null

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoTitle = videoList[position].title
        //holder.videoThumbnail = videoList[position].imagePath.toUri()

        holder.videoThumbnailImageView.setOnClickListener{
            listener?.onRecyclerItemClick(videoList[position])
        }
    }

    fun removeVideo(video: VideoApp){
        videoList.remove(video)
    }

    fun addVideo(video: VideoApp){
        videoList.add(video)
    }

    fun changeVideoList(newVideoList: ArrayList<VideoApp>){
        videoList = newVideoList
    }

    class ViewHolder(itemView: View,
                     val videoThumbnailImageView: ImageView,
                     private val videoTitleTextView: TextView): RecyclerView.ViewHolder(itemView){
        var videoTitle: String?
            get(){
                return videoTitleTextView.text.toString()
            }
            set(value){
                videoTitleTextView.text = value
            }
        var videoThumbnail: Uri?
            set(value){
                videoThumbnailImageView.setImageURI(value)
            }
            get(){
                /** i cannot get uri of an imageview, but will never need the getter, is it okay to implement a wrong getter then?**/
                return "".toUri()
            }
    }
}