package de.tdsoftware.moviesharing.video

import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.ViewCompat
import de.tdsoftware.moviesharing.BaseActivity
import de.tdsoftware.moviesharing.R

class VideoDetailsActivity : BaseActivity(), VideoDetailsActivityView.Listener {

    companion object{
        val imageBanner = "imageBanner"
        val imageCover = "imageCover"
    }
    private lateinit var mainView: VideoDetailsActivityView
    private lateinit var video: MediaStore.Video // later this is gonna be the video-type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = layoutInflater.inflate(R.layout.activity_video_details, null,false) as VideoDetailsActivityView
        setContentView(mainView)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        //here we have to check for favorite-state of the object we have!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                super.onBackPressed()
            }
            R.id.favorite_item -> {
                // TODO("here we handle the favorite selected")
                Toast.makeText(this, "FavoriteOnClick", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRatingChanged(rating: Float) {
        TODO("here we handle ratingchange")
    }
}
