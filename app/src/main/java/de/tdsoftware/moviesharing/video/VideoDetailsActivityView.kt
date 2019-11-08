package de.tdsoftware.moviesharing.video

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.ViewCompat
import de.tdsoftware.moviesharing.R
import kotlinx.android.synthetic.main.activity_video_details.view.*

class VideoDetailsActivityView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    // region Public Types

    interface Listener {
        fun onRatingChanged(rating: Float)
    }

    // endregion

    // region Properties

    var viewListener: Listener = context as Listener
    private lateinit var bannerImageView: ImageView
    private lateinit var coverImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var secondaryTextView: TextView
    private lateinit var descriptionTextView: TextView


    private var videoTitle: String?
        get(){
            return titleTextView.text.toString()
        }
        set(value){
            titleTextView.text = value
        }

    private var videoDescription: String?
        get(){
            return descriptionTextView.text.toString()
        }
        set(value){
            descriptionTextView.text = value
        }

    private var videoSecondaryText: String?
        get(){
            return secondaryTextView.text.toString()
        }
        set(value){
            secondaryTextView.text = value
        }

    private lateinit var videoRatingBar: RatingBar

    // endregion

    // region Constructors

    // endregion
    // region View Lifecycle
    override fun onFinishInflate() {
        super.onFinishInflate()
        postLayoutInitialize()
        setTransitionNames()
    }

    // endregion

    // region Private API

    private fun postLayoutInitialize() {
        bindViews()
        setupControls()
    }

    private fun bindViews() {
        videoRatingBar = findViewById(R.id.activity_video_details_rating_bar)
        bannerImageView = findViewById(R.id.activity_video_details_banner_image_view)
        coverImageView = findViewById(R.id.activity_video_details_cover_image_view)
        titleTextView = findViewById(R.id.activity_video_details_title_text_view)
    }

    private fun setupControls() {
        videoRatingBar.setOnRatingBarChangeListener{ _, value: Float, fromUser: Boolean ->
            if(fromUser){
                viewListener.onRatingChanged(value)
            }
        }
    }

    private fun setTransitionNames(){
        ViewCompat.setTransitionName(bannerImageView, VideoDetailsActivity.imageBanner)
        ViewCompat.setTransitionName(coverImageView, VideoDetailsActivity.imageCover)
    }

    // endregion 
}