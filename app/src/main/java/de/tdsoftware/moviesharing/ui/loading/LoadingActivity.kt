package de.tdsoftware.moviesharing.ui.loading


import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import de.tdsoftware.moviesharing.ui.BaseActivity
import de.tdsoftware.moviesharing.ui.main.MainActivity
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.util.MoviesManager
import de.tdsoftware.moviesharing.util.NetworkErrorEvent
import de.tdsoftware.moviesharing.util.NetworkSuccessEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.coroutines.CoroutineContext

/**
 * LoadingActivity represents the starting-activity of the application, basically making sure that data
 * is being obtained and if so redirects to MainActivity
 */

class LoadingActivity : BaseActivity() {

    private lateinit var mainView: LoadingActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)

        mainView = layoutInflater.inflate(R.layout.activity_loading, null, false) as LoadingActivityView
        setContentView(mainView)

        MoviesManager.setUpMoviesManager(applicationContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE))
        MoviesManager.fetchPlaylistList()

        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.backgroundDefault)))

        setUpMainView()
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }

    private fun setUpMainView() {
        mainView.viewListener = object: LoadingActivityView.Listener{
            override fun onRetryButtonClicked() {
                MoviesManager.fetchPlaylistList()
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onNetworkSuccessEvent(networkSuccessEvent: NetworkSuccessEvent){
        val intent = Intent(this@LoadingActivity, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkErrorEvent(networkErrorEvent: NetworkErrorEvent){
        Toast.makeText(this, networkErrorEvent.message,Toast.LENGTH_LONG).show()
        mainView.showRetryButton(true)
    }
}
