package de.tdsoftware.moviesharing.ui.main.favorites

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragmentView

class FavoritesFragmentView(context: Context, attrs: AttributeSet?) :
    MoviesBaseFragmentView(context, attrs) {

    // region Public Types

    interface Listener {
        fun onQueryChange(newText: String)
    }

    // endregion

    // region Properties

    var viewListener: Listener? = null

    var searchViewQuery: String
        get() {
            return searchView.query.toString()
        }
        set(value) {
            searchView.setQuery(value, true)
        }

    var hintText: String
        get() {
            return hintTextView.text.toString()
        }
        set(value) {
            hintTextView.text = value
        }

    private lateinit var searchView: SearchView
    private lateinit var hintTextView: TextView
    private lateinit var hintLayout: ConstraintLayout

    // endregion

    // region public API

    fun changeEmptyStateTextVisibility(isVisible: Boolean) {
        if (isVisible) {
            hintLayout.visibility = View.VISIBLE
        } else {
            hintLayout.visibility = View.INVISIBLE
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
        buildRecyclerView()
    }

    private fun bindViews() {
        playlistRecyclerView = findViewById(R.id.fragment_favorites_recycler_view_favorite_playlist)
        hintLayout = findViewById(R.id.fragment_favorites_empty_state_layout)
        hintTextView = findViewById(R.id.fragment_favorites_empty_state_text_view)
        searchView = findViewById(R.id.fragment_favorites_search_view)
    }

    private fun setupControls() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewListener?.onQueryChange(newText)
                }
                return true
            }
        })
    }

    private fun buildRecyclerView() {
        playlistRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    // endregion 
}