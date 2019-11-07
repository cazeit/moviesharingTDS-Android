package de.tdsoftware.moviesharing.main.movies

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import de.tdsoftware.moviesharing.R

class MoviesFragmentView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    // region Public Types

    interface Listener {
        // TODO: add interactions
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

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
        tabLayout = findViewById(R.id.fragment_movies_tab_layout)
        viewPager = findViewById(R.id.fragment_movies_view_pager)
    }

    private fun setupControls() {
        val x = context as AppCompatActivity
        val adapter = TabViewAdapter(x.supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    // endregion 
}