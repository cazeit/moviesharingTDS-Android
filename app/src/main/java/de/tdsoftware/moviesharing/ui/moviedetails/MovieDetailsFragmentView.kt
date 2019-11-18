package de.tdsoftware.moviesharing.ui.moviedetails

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import de.tdsoftware.moviesharing.R

class MovieDetailsFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    // region Public Types

    interface Listener {
        fun onRatingChanged(rating: Float)
        fun onCoverImageClick()
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null

    var title: String?
        get() {
            return titleTextView.text.toString()
        }
        set(value) {
            titleTextView.text = value
        }

    var description: String?
        get() {
            return descriptionTextView.text.toString()
        }
        set(value) {
            descriptionTextView.text = value
        }

    var secondaryText: String?
        get() {
            return secondaryTextView.text.toString()
        }
        set(value) {
            secondaryTextView.text = value
        }

    var ratingBarValue: Float
        get() {
            return ratingBar.rating
        }
        set(value) {
            ratingBar.rating = value
        }


    private lateinit var bannerImageView: ImageView
    private lateinit var coverImageView: ImageView

    private lateinit var titleTextView: TextView
    private lateinit var secondaryTextView: TextView
    private lateinit var descriptionTextView: TextView

    private lateinit var ratingBar: RatingBar

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
        ratingBar = findViewById(R.id.fragment_movie_details_rating_bar)
        bannerImageView = findViewById(R.id.fragment_movie_details_banner_image_view)
        coverImageView = findViewById(R.id.fragment_movie_details_cover_image_view)
        titleTextView = findViewById(R.id.fragment_movie_details_title_text_view)
        descriptionTextView = findViewById(R.id.fragment_movie_details_description_text_view)
        secondaryTextView = findViewById(R.id.fragment_movie_details_secondary_text_view)
    }

    private fun setupControls() {
        ratingBar.setOnRatingBarChangeListener { _, value: Float, fromUser: Boolean ->
            if(fromUser) {
                viewListener?.onRatingChanged(value)
            }
        }

        coverImageView.setOnClickListener {
            viewListener?.onCoverImageClick()
        }
    }

    // endregion 
}