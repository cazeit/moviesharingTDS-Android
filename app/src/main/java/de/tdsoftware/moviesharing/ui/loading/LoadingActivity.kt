package de.tdsoftware.moviesharing.ui.loading


import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.ui.main.MainActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.Sample
import de.tdsoftware.moviesharing.util.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * LoadingActivity represents the starting-activity of the application, basically making sure that data
 * is being obtained and if so redirects to MainActivity
 */

// channel ID: UCPppOIczZfCCoqAwRLc4T0A
// API-Key: AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU

class LoadingActivity : BaseActivity() {

    private lateinit var mainView: LoadingActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: remove after implementation of FavoritesInit (delete shared prefs atm, as favorite list is not initialized
        getSharedPreferences("sharedPref", Context.MODE_PRIVATE).edit().clear().apply()

        mainView = layoutInflater.inflate(R.layout.activity_loading, null, false) as LoadingActivityView
        setContentView(mainView)

        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.backgroundDefault)))

        GlobalScope.launch {
            delay(3000)

            Repository.playlistList = Sample.playlistSampleList
            Repository.favoritePlaylistList = arrayListOf(Sample.playlistFavorite)

            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
