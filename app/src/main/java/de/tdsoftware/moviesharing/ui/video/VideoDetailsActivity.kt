package de.tdsoftware.moviesharing.ui.video

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.VideoApp

class VideoDetailsActivity : BaseActivity(){

    private val videoDetailsFragment by lazy{
        VideoDetailsFragment.newInstance()
    }
    private lateinit var mainView: VideoDetailsActivityView
    private lateinit var video: VideoApp
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView =
            layoutInflater.inflate(R.layout.activity_video_details, null,false) as VideoDetailsActivityView
        setContentView(mainView)

        video = intent.getSerializableExtra("video") as VideoApp
        sharedPreferences = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)

        //show fragment
        supportFragmentManager.beginTransaction().replace(R.id.activity_video_details_container, videoDetailsFragment).commit()

        setUpMainView()

        setUpActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        if(video.isFavorite){
            menu.findItem(R.id.favorite_item).icon.colorFilter =
                    PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpMainView(){
    }

    private fun setUpActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = video.title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                super.onBackPressed()
            }
            R.id.favorite_item -> {
                //set colorfilter when no filter is there -> switching between blue and gray
                if(video.isFavorite){
                    item.icon.colorFilter = null
                }else{
                    item.icon.colorFilter =
                            PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
                }
                video.isFavorite = !video.isFavorite
                sharedPreferences.edit().putBoolean(video.id + "_favorite", video.isFavorite).apply()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
