package de.tdsoftware.moviesharing.ui.loading

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import de.tdsoftware.moviesharing.R

class LoadingActivityView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    // region public types

    interface Listener {
        fun onRetryButtonClicked()
        fun onYoutubeApiButtonClicked()
        fun onVimeoApiButtonClicked()
    }

    // endregion

    // region Properties

    private lateinit var hasSourceLayout: ConstraintLayout

    private lateinit var progressBarText: TextView

    private lateinit var retryButton: Button
    private lateinit var vimeoApiButton: Button
    private lateinit var youtubeApiButton: Button

    private lateinit var progressBar: ProgressBar
    var viewListener: Listener? = null

    // endregion

    // region Constructors

    // endregion

    // region public API

    fun changeRetryButtonVisibility(isButtonVisible: Boolean) {
        changeButtonVisibility(isButtonVisible, retryButton)
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
        hasSourceLayout = findViewById(R.id.activity_loading_has_source_layout)

        progressBarText = findViewById(R.id.activity_loading_progress_bar_text)
        progressBar = findViewById(R.id.activity_loading_progress_bar)

        retryButton = findViewById(R.id.activity_loading_retry_button)
        vimeoApiButton = findViewById(R.id.activity_loading_vimeo_button)
        youtubeApiButton = findViewById(R.id.activity_loading_youtube_button)
    }

    private fun setupControls() {
        retryButton.setOnClickListener {
            retryButton.isEnabled = false
            changeRetryButtonVisibility(false)

            viewListener?.onRetryButtonClicked()
            retryButton.isEnabled = true
        }

        youtubeApiButton.setOnClickListener {
            youtubeApiButton.isEnabled = false
            hasSourceLayout.visibility = View.VISIBLE
            changeButtonVisibility(false, youtubeApiButton, vimeoApiButton)

            viewListener?.onYoutubeApiButtonClicked()
            youtubeApiButton.isEnabled = true
        }

        vimeoApiButton.setOnClickListener {
            vimeoApiButton.isEnabled = false
            hasSourceLayout.visibility = View.VISIBLE
            changeButtonVisibility(false, youtubeApiButton, vimeoApiButton)

            viewListener?.onVimeoApiButtonClicked()
            vimeoApiButton.isEnabled = true
        }
    }

    private fun changeButtonVisibility(isButtonVisible: Boolean, vararg buttonSet: Button) {
        if (isButtonVisible) {
            progressBar.visibility = View.GONE
            progressBarText.visibility = View.GONE
            for(button in buttonSet) {
                button.visibility = View.VISIBLE
            }
        } else {
            progressBar.visibility = View.VISIBLE
            progressBarText.visibility = View.VISIBLE
            for(button in buttonSet) {
                button.visibility = View.GONE
            }
        }
    }

    // endregion 
}