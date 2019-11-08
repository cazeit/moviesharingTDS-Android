package de.tdsoftware.moviesharing.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.util.Pair
import androidx.core.app.ActivityOptionsCompat
import de.tdsoftware.moviesharing.BaseActivity
import de.tdsoftware.moviesharing.BaseFragment
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.favorites.FavoritesFragment
import de.tdsoftware.moviesharing.main.movies.MoviesFragment
import de.tdsoftware.moviesharing.main.movies.adapter.VideoBaseAdapter
import de.tdsoftware.moviesharing.video.VideoDetailsActivity

class MainActivity : BaseActivity(), MainActivityView.Listener, VideoBaseAdapter.ItemClickListener {

    private val moviesFragment by lazy{
        MoviesFragment.newInstance()
    }
    private val favoritesFragment by lazy{
        FavoritesFragment.newInstance()
    }

    private lateinit var mainView: MainActivityView

    private var currentFragment: BaseFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = layoutInflater.inflate(R.layout.activity_main, null, false) as MainActivityView
        setContentView(mainView)

        //but it gets reset when turning as well...
        if(currentFragment == null){
            println("currentfragent is null")
            currentFragment = moviesFragment
        }
        showFragment(currentFragment!!)
    }

    override fun onMoviesSelected() {
        showFragment(moviesFragment)
    }

    override fun onFavoritesSelected() {
        showFragment(favoritesFragment)
    }

    private fun showFragment(fragment: BaseFragment){
        supportFragmentManager.beginTransaction().replace(R.id.activity_main_container,fragment).commit()
        currentFragment = fragment
    }

    override fun onRecyclerItemClick(position: Int, view: View) {
        Toast.makeText(this, "Item No.$position was clicked!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, VideoDetailsActivity::class.java)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
            Pair(view, VideoDetailsActivity.imageBanner),
            Pair(view, VideoDetailsActivity.imageCover))
        startActivity(intent,activityOptions.toBundle())
    }

}
