package de.tdsoftware.moviesharing.loading


import android.content.Intent
import android.os.Bundle
import de.tdsoftware.moviesharing.BaseActivity
import de.tdsoftware.moviesharing.main.MainActivity
import de.tdsoftware.moviesharing.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : BaseActivity(),
    LoadingActivityView.Listener {

    private lateinit var mainView: LoadingActivityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = layoutInflater.inflate(R.layout.activity_loading, null, false) as LoadingActivityView
        setContentView(mainView)

        GlobalScope.launch {
            delay(3000)
            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
