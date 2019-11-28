package de.tdsoftware.moviesharing.ui.moviedetails

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.util.MoviesManager

/**
 * Activity that has one fragment in it (MovieDetailsFragment), basically just inflates it and handles
 * optionsMenu and actionBar
 */
class MovieDetailsActivity : BaseActivity() {

    // region properties

    private val movieDetailsFragment by lazy {
        MovieDetailsFragment.newInstance()
    }
    private lateinit var mainView: MovieDetailsActivityView
    private lateinit var movie: Movie
    private lateinit var sharedPreferences: SharedPreferences

    private val logTag = MovieDetailsActivity::class.java.simpleName

    // endregion

    // region lifecycle callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView =
            layoutInflater.inflate(
                R.layout.activity_movie_details,
                null,
                false
            ) as MovieDetailsActivityView
        setContentView(mainView)

        movie = intent.getSerializableExtra("movie") as Movie
        sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_movie_details_container, movieDetailsFragment).commit()

        setUpMainView()

        setUpActionBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        MoviesManager.updateFavorites(
            movie,
            sharedPreferences.getBoolean(movie.id + "_favorite", false)
        )
    }

    /**
     * check if movie is favorite or not and display the icon in the corresponding color
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        if (sharedPreferences.getBoolean(movie.id + "_favorite", false)) {
            menu.findItem(R.id.favorite_item).icon.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_IN
                )
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * handle back-press in actionbar and favoriteIconOnClick -> write down to sharedPreferences
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
            }
            R.id.favorite_item -> {
                if (sharedPreferences.getBoolean(movie.id + "_favorite", false)) {
                    Log.v(logTag, "Movie is no longer a favorite")

                    item.icon.colorFilter = null
                    sharedPreferences.edit().putBoolean(movie.id + "_favorite", false).apply()
                } else {
                    Log.v(logTag, "Movie is now a favorite")

                    item.icon.colorFilter =
                        PorterDuffColorFilter(
                            ContextCompat.getColor(this, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN
                        )
                    sharedPreferences.edit().putBoolean(movie.id + "_favorite", true).apply()
                }
            }
        }
        return true
    }

    // endregion

    // region private API

    private fun setUpMainView() {

    }

    /**
     * enable back-button and set title of actionbar to movies title
     */
    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = movie.title
    }
    // endregion
}
