package de.tdsoftware.moviesharing.main.movies

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet

abstract class MoviesViewFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    interface Listener {
        fun onItemClick()
    }

    var viewListener: Listener? = null
}