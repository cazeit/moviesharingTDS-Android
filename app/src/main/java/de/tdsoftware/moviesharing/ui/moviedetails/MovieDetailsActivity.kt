package de.tdsoftware.moviesharing.ui.moviedetails

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.util.FavoriteUpdateEvent
import org.greenrobot.eventbus.EventBus

class MovieDetailsActivity : BaseActivity(){

    private val movieDetailsFragment by lazy{
        MovieDetailsFragment.newInstance()
    }
    private lateinit var mainView: MovieDetailsActivityView
    private lateinit var movie: Movie
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView =
            layoutInflater.inflate(R.layout.activity_movie_details, null,false) as MovieDetailsActivityView
        setContentView(mainView)

        movie = intent.getSerializableExtra("movie") as Movie
        sharedPreferences = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

        supportFragmentManager.beginTransaction().replace(R.id.activity_movie_details_container, movieDetailsFragment).commit()

        setUpMainView()

        setUpActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        if(sharedPreferences.getBoolean(movie.id + "_favorite", false)){
            menu.findItem(R.id.favorite_item).icon.colorFilter =
                    PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpMainView(){
    }

    private fun setUpActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = movie.title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                super.onBackPressed()
            }
            R.id.favorite_item -> {
                if(sharedPreferences.getBoolean(movie.id + "_favorite", false)){
                    item.icon.colorFilter = null
                    sharedPreferences.edit().putBoolean(movie.id + "_favorite", false).apply()
                }else{
                    item.icon.colorFilter =
                            PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
                    sharedPreferences.edit().putBoolean(movie.id + "_favorite", true).apply()
                }
                val event = FavoriteUpdateEvent()
                event.movie = movie
                EventBus.getDefault().post(event)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
