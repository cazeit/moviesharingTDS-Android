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

    //TODO: content as fragment, not as activity
    private lateinit var mainView: VideoDetailsActivityView
    private lateinit var video: VideoApp
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = layoutInflater.inflate(R.layout.activity_video_details, null,false) as VideoDetailsActivityView
        setContentView(mainView)

        video = intent.getSerializableExtra("video") as VideoApp
        sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        setUpMainView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        mainView.viewListener = object: VideoDetailsActivityView.Listener{
            override fun onRatingChanged(rating: Float) {
                video.rating = rating
                sharedPreferences.edit().putFloat(video.id + "_rating", video.rating).apply()
            }
        }

        mainView.videoTitle = video.title
        mainView.videoSecondaryText = video.secondaryText
        //TODO: missin imageView
        mainView.videoDescription = video.description
        mainView.videoRatingBarValue = video.rating
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
