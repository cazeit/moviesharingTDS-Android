package de.tdsoftware.moviesharing.video

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet

class VideoDetailsActivityView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    // region Public Types

    interface Listener {
        // TODO: add interactions
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null
    // private lateinit var myView: View

    // endregion

    // region Constructors

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
        // myView = findViewById(R.id.myView)
    }

    private fun setupControls() {

    }

    // endregion 
}