package de.tdsoftware.moviesharing.ui.video

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.R

class VideoDetailsFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    // region Public Types

    interface Listener {
        fun onRatingChanged(rating: Float)
    }

    // endregion

    // region Properties

    var viewListener: VideoDetailsFragmentView.Listener? = null


    private lateinit var bannerImageView: ImageView

    private lateinit var coverImageView: ImageView

    private lateinit var titleTextView: TextView

    private lateinit var secondaryTextView: TextView

    private lateinit var descriptionTextView: TextView

    private lateinit var videoRatingBar: RatingBar


    var videoTitle: String?
        get(){
            return titleTextView.text.toString()
        }
        set(value){
            titleTextView.text = value
        }

    var videoDescription: String?
        get(){
            return descriptionTextView.text.toString()
        }
        set(value){
            descriptionTextView.text = value
        }

    var videoSecondaryText: String?
        get(){
            return secondaryTextView.text.toString()
        }
        set(value){
            secondaryTextView.text = value
        }

    var videoRatingBarValue: Float
        get(){
            return videoRatingBar.rating
        }
        set(value){
            videoRatingBar.rating = value
        }

    // endregion

    // region View Lifecycle
    override fun onFinishInflate() {
        super.onFinishInflate()
        postLayoutInitialize()
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
        descriptionTextView = findViewById(R.id.activity_video_details_description_text_view)
        secondaryTextView = findViewById(R.id.activity_video_details_secondary_text_view)
    }

    //suppresses "performClick"-hint
    @SuppressLint("ClickableViewAccessibility")
    private fun setupControls() {
        videoRatingBar.setOnRatingBarChangeListener{ _, value: Float, fromUser: Boolean ->
            if(fromUser){
                viewListener?.onRatingChanged(value)
            }
        }

        bannerImageView.setOnTouchListener(object: OnTouchListener{

            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                val image = view as ImageView
                val rect = Rect(view.left, view.top, view.right, view.bottom)
                when(event?.action){
                    MotionEvent.ACTION_DOWN->{
                        view.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, R.color.colorHighlight), PorterDuff.Mode.LIGHTEN)
                        return true
                    }
                    MotionEvent.ACTION_UP->{
                        view.colorFilter = null
                        if(rect.contains(event.x.toInt() + view.left, event.y.toInt() + view.top)){
                            Toast.makeText(context, "BannerOnRelease", Toast.LENGTH_SHORT).show()
                            //TODO:here we have to implement the YouTube-Intent-call
                        }
                        return true
                    }
                    MotionEvent.ACTION_CANCEL->{
                        view.colorFilter = null
                        return true
                    }
                }
                return true
            }
        })
    }

    // endregion 
}