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

    interface Listener{
        fun onRetryButtonClicked()
    }

    // endregion

    // region Properties

    var loadingText: String?
        get() {
            return progressBarText.text.toString()
        }
        set(value) {
            progressBarText.text = value
        }

    private lateinit var progressBarText: TextView
    private lateinit var retryButton: Button
    private lateinit var progressBar: ProgressBar
    var viewListener: Listener? = null

    // endregion

    // region Constructors

    // endregion

    // region public API

    fun showRetryButton(isButtonVisible: Boolean){
        if(isButtonVisible) {
            progressBar.visibility = View.GONE
            progressBarText.visibility = View.GONE
            retryButton.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.VISIBLE
            progressBarText.visibility = View.VISIBLE
            retryButton.visibility = View.GONE
        }
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
        progressBarText = findViewById(R.id.activity_loading_progress_bar_text)
        retryButton = findViewById(R.id.activity_loading_retry_button)
        progressBar = findViewById(R.id.activity_loading_progress_bar)
    }

    private fun setupControls() {
        retryButton.setOnClickListener{
            showRetryButton(false)

            viewListener?.onRetryButtonClicked()
        }
    }

    // endregion 
}