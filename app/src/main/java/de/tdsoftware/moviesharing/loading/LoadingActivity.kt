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

    private lateinit var view: LoadingActivityView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        view = findViewById(R.id.activity_loading_view)
        GlobalScope.launch {
            delay(3000)
            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
