package de.tdsoftware.moviesharing.ui.main

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.tdsoftware.moviesharing.R

class MainActivityView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    // region Public Types

    interface Listener {
        fun onMoviesSelected()
        fun onFavoritesSelected()
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null
    private lateinit var bottomNavigationView: BottomNavigationView

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
        bottomNavigationView = findViewById(R.id.activity_main_bottom_nav)
    }

    private fun setupControls() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it) {
                 bottomNavigationView.menu.getItem(0) -> {
                     viewListener?.onMoviesSelected()
                 }
                bottomNavigationView.menu.getItem(1) -> {
                    viewListener?.onFavoritesSelected()
                }
            }
            true
        }
    }

    // endregion 
}