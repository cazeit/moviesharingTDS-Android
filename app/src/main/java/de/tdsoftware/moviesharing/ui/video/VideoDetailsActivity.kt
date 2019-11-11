package de.tdsoftware.moviesharing.ui.video

import android.graphics.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.R

class VideoDetailsActivity : BaseActivity(){

    private lateinit var mainView: VideoDetailsActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = layoutInflater.inflate(R.layout.activity_video_details, null,false) as VideoDetailsActivityView
        setContentView(mainView)
        setUpMainView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpMainView(){
        mainView.viewListener = object: VideoDetailsActivityView.Listener{
            override fun onRatingChanged(rating: Float) {
                // TODO("here we handle rating changed")
                Toast.makeText(this@VideoDetailsActivity, "RatingChanged",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                super.onBackPressed()
            }
            R.id.favorite_item -> {
                //set colorfilter when no filter is there -> switching between blue and gray
                if(item.icon.colorFilter == null){
                    item.icon.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
                }else{
                    item.icon.colorFilter = null
                }
                // TODO("here we handle the favorite selected")
                Toast.makeText(this, "FavoriteOnClick", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
