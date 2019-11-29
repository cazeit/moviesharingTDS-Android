package de.tdsoftware.moviesharing.ui.loading

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.ui.main.MainActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.util.MoviesManager
import de.tdsoftware.moviesharing.util.NetworkManager
import de.tdsoftware.moviesharing.util.Notification

/**
 * LoadingActivity is the starting-activity of the application, basically making sure that data
 * is being obtained and if so redirects to MainActivity / displays error via SnackBar
 */

// TODO: implement choose API button and set layout to gone, after that normal exisiting flow ( but set API-enum in NetworkManager)
class LoadingActivity : BaseActivity() {

    // region properties

    private lateinit var mainView: LoadingActivityView

    // endregion

    // region EventBus
    /**
     * Subscriber-method for EventBus:
     * Check what type the notification is and handle it
     * (data loaded -> start activity)
     * (error loading -> show retry-button)
     */
    override fun onNotification(notification: Notification) {
        super.onNotification(notification)
        when (notification) {
            is Notification.NetworkErrorEvent -> {
                mainView.changeRetryButtonVisibility(true)
            }
            is Notification.PlaylistChangedEvent -> {
                val intent = Intent(this@LoadingActivity, MainActivity::class.java)
                startActivity(intent)
                // fade transition
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }

    // endregion

    // region lifecycle callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView =
            layoutInflater.inflate(R.layout.activity_loading, null, false) as LoadingActivityView
        setContentView(mainView)

        // set sharedPrefs in MoviesManager
        setUpManagers()

        MoviesManager.fetchPlaylistListWithMovies()

        actionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.backgroundDefault
                )
            )
        )

        setUpMainView()
    }

    // endregion

    // private API

    private fun setUpManagers() {
        MoviesManager.setUpMoviesManager(
            applicationContext.getSharedPreferences(
                "sharedPref",
                Context.MODE_PRIVATE
            )
        )

        NetworkManager.changeSourceApi(NetworkManager.ApiName.YOUTUBE)
    }

    private fun setUpMainView() {
        mainView.viewListener = object : LoadingActivityView.Listener {
            override fun onRetryButtonClicked() {
                MoviesManager.fetchPlaylistListWithMovies()
            }

        }
    }

    // endregion
}
