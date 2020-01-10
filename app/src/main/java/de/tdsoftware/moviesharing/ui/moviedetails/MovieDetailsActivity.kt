package de.tdsoftware.moviesharing.ui.moviedetails

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.model.Movie
import de.tdsoftware.moviesharing.util.MoviesManager
import de.tdsoftware.moviesharing.util.Notification

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

    private lateinit var menu: Menu
    private lateinit var favoriteMenuItem: MenuItem

    // endregion
    override fun onNotification(notification: Notification) {
        super.onNotification(notification)
        when (notification) {
            is Notification.NetworkErrorEvent -> {
                changeFavoriteMenuItemColor()
            }
            is Notification.MovieFavoriteStatusChangedEvent -> {
                sharedPreferences.edit().putBoolean(movie.id + "_favorite", notification.isFavorite).apply()
            }
        }
    }

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
        this.menu = menu
        favoriteMenuItem = menu.findItem(R.id.favorite_item)
        if (sharedPreferences.getBoolean(movie.id + "_favorite", false)) {
            favoriteMenuItem.icon.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_IN
                )
        }

        return super.onCreateOptionsMenu(menu)
    }


    //
    /**
     * handle back-press in actionbar and favoriteIconOnClick -> write down to sharedPreferences
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
            }
            R.id.favorite_item -> {
                changeMovieFavoriteState()
            }
        }
        return true
    }

    // endregion

    // region private API

    private fun changeMovieFavoriteState() {
        if (sharedPreferences.getBoolean(movie.id + "_favorite", false)) {
            removeMovieFromFavorites()
        } else {
            addMovieToFavorites()
        }
    }

    private fun removeMovieFromFavorites() {
        MoviesManager.changeFavoriteStatus(movie, false)

        changeFavoriteMenuItemColor()
    }

    private fun addMovieToFavorites() {
        MoviesManager.changeFavoriteStatus(movie, true)

        changeFavoriteMenuItemColor()
    }

    private fun changeFavoriteMenuItemColor(){
        if(favoriteMenuItem.icon.colorFilter == null) {
            favoriteMenuItem.icon.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_IN
                )
        } else {
            favoriteMenuItem.icon.colorFilter = null
        }
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