package de.tdsoftware.moviesharing.ui.loading

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import de.tdsoftware.moviesharing.R

class LoadingActivityView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    // region Properties

    var loadingText: String?
        get() {
            return startLoadingText.text.toString()
        }
        set(value){
            startLoadingText.text = value
        }

    private lateinit var startLoadingText: TextView

    // endregion

    // region Constructors

    // endregion

    // region public API

    // enregion

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
        startLoadingText = findViewById(R.id.activity_loading_progress_bar_text)
    }

    private fun setupControls() {

    }

    // endregion 
}