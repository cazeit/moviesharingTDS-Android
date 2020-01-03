package de.tdsoftware.moviesharing.ui.moviedetails

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.util.NetworkManager
import jp.wasabeef.picasso.transformations.CropTransformation
import java.lang.Exception

class MovieDetailsFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    // region Public Types

    companion object{
        const val CARD_BANNER = "imageBanner"
        const val CARD_COVER = "imageCover"
    }

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

    private lateinit var bannerImageCardView: CardView
    private lateinit var coverImageCardView: CardView

    private lateinit var titleTextView: TextView
    private lateinit var secondaryTextView: TextView
    private lateinit var descriptionTextView: TextView

    private lateinit var ratingBar: RatingBar

    private var exiting = false

    // endregion

    // region public API

    fun loadImages(url: String?) {
        val picasso = Picasso.get().load(url)
        if(NetworkManager.sourceApi == NetworkManager.ApiName.YOUTUBE) {
            val transformation = CropTransformation(160,240)
            picasso.transform(transformation)
        }
        picasso.into(bannerImageView)
        picasso.into(coverImageView)
    }

    fun enableButtons() {
        coverImageView.isEnabled = true
    }

    fun finalizeTransition(){
        if(!exiting) {
            bannerImageCardView.radius = 0f
        }
        exiting = true
    }

    fun prepareTransition(){
        bannerImageCardView.radius = resources.getDimension(R.dimen.corner_radius_normal)
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
        ratingBar = findViewById(R.id.fragment_movie_details_rating_bar)

        bannerImageView = findViewById(R.id.fragment_movie_details_banner_image_view)
        coverImageView = findViewById(R.id.fragment_movie_details_cover_image_view)

        bannerImageCardView = findViewById(R.id.fragment_movie_details_banner_image_card_view)
        coverImageCardView = findViewById(R.id.fragment_movie_details_cover_card_view)

        titleTextView = findViewById(R.id.fragment_movie_details_title_text_view)
        descriptionTextView = findViewById(R.id.fragment_movie_details_description_text_view)
        secondaryTextView = findViewById(R.id.fragment_movie_details_secondary_text_view)

        ViewCompat.setTransitionName(bannerImageCardView, CARD_BANNER)
        ViewCompat.setTransitionName(coverImageCardView, CARD_COVER)
    }

    private fun setupControls() {
        ratingBar.setOnRatingBarChangeListener { _, value: Float, fromUser: Boolean ->
            if (fromUser) {
                viewListener?.onRatingChanged(value)
            }
        }

        coverImageView.setOnClickListener {
            coverImageView.isEnabled = false
            viewListener?.onCoverImageClick()
        }
    }

    // endregion 
}