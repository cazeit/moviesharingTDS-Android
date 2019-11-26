package de.tdsoftware.moviesharing.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import de.tdsoftware.moviesharing.ui.loading.LoadingActivity
import de.tdsoftware.moviesharing.util.Notification
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * BaseActivity for all Activities in this application
 */
abstract class BaseActivity : AppCompatActivity() {

    // region EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onNotification(notification: Notification) {
        when (notification) {
            is Notification.NetworkErrorEvent -> {
                val parentView = findViewById<View>(android.R.id.content)
                Snackbar.make(
                    parentView,
                    "Error Nr. " + notification.code + ": " + notification.message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    // endregion

    // region lifecycle callbacks

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    /**
     * workaround so LoadingActivity gets called each time the processid is not the same as
     * it was saved before (process was killed meanwhile), as we don't use local storage we just refetch data.
     */
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val currentProcessId = android.os.Process.myPid().toString()
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("PID", "") != currentProcessId) {
                val intent = Intent(this, LoadingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("PID", android.os.Process.myPid().toString())
    }

    // endregion
}
