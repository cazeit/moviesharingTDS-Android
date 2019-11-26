package de.tdsoftware.moviesharing.ui.main

import android.os.Bundle
import android.util.Log
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.favorites.FavoritesFragment
import de.tdsoftware.moviesharing.ui.main.movies.MoviesFragment

/**
 * Activity with various inside fragments.
 * Activity on its own only handles switching between FavoriteFragment and MoviesFragment via Bottom-
 * Navigation-View using supportFragmentManager
 */

class MainActivity : BaseActivity() {

    // region properties

    private lateinit var mainView: MainActivityView
    private var currentFragment: BaseFragment? = null

    private val moviesFragment by lazy {
        MoviesFragment.newInstance()
    }

    private val favoritesFragment by lazy {
        FavoritesFragment.newInstance()
    }

    private val logTag = MainActivity::class.java.simpleName

    // endregion

    // region lifecycle callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = layoutInflater.inflate(R.layout.activity_main, null, false) as MainActivityView
        setContentView(mainView)
        setUpMainView()

        showFragment(moviesFragment)
    }
    // endregion

    // region private API

    private fun setUpMainView() {
        mainView.viewListener = object : MainActivityView.Listener {

            override fun onMoviesSelected() {
                showFragment(moviesFragment)
            }

            override fun onFavoritesSelected() {
                showFragment(favoritesFragment)
            }
        }
    }

    private fun showFragment(fragment: BaseFragment) {
        if (currentFragment == fragment) {
            return
        }

        Log.v(logTag, "Showing " + fragment::class.java.simpleName)

        currentFragment = fragment
        supportFragmentManager.beginTransaction().replace(R.id.activity_main_container, fragment)
            .commit()
    }

    // endregion
}
