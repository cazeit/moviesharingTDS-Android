package de.tdsoftware.moviesharing.ui.loading


import android.content.Context
import android.content.Intent
import android.os.Bundle
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.ui.main.MainActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.Sample
import de.tdsoftware.moviesharing.util.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : BaseActivity(){

    private lateinit var mainView: LoadingActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: remove after implementation of FavoritesInit
        getSharedPreferences("sharedPref", Context.MODE_PRIVATE).edit().clear().apply()
        mainView = layoutInflater.inflate(R.layout.activity_loading, null, false) as LoadingActivityView
        setContentView(mainView)

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
