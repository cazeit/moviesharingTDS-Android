package de.tdsoftware.moviesharing.main

import android.os.Bundle
import de.tdsoftware.moviesharing.BaseActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.favorites.FavoritesFragment
import de.tdsoftware.moviesharing.main.movies.MoviesFragment

class MainActivity : BaseActivity(), MainActivityView.Listener {

    private val moviesFragemnt = MoviesFragment.newInstance()
    private val favoritesFragment = FavoritesFragment.newInstance()
    private lateinit var view: MainActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById(R.id.activity_main_view)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.activity_main_container, moviesFragemnt,"movies").commit()
            supportFragmentManager.beginTransaction()
                    .add(R.id.activity_main_container, favoritesFragment,"favorites").hide(favoritesFragment).commit()
        }
    }

    override fun onMoviesSelected() {
        val favoritesFragment = supportFragmentManager.findFragmentByTag("favorites")
        val moviesFragment = supportFragmentManager.findFragmentByTag("movies")
        if(favoritesFragment != null && moviesFragment != null) {
            supportFragmentManager.beginTransaction().hide(favoritesFragment).show(moviesFragment)
                .commit()
        }
    }

    override fun onFavoritesSelected() {
        val favoritesFragment = supportFragmentManager.findFragmentByTag("favorites")
        val moviesFragment = supportFragmentManager.findFragmentByTag("movies")
        if(favoritesFragment != null && moviesFragment != null) {
            supportFragmentManager.beginTransaction().hide(moviesFragment).show(favoritesFragment)
                .commit()
        }
    }
}
