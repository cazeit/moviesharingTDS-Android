package de.tdsoftware.moviesharing.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import de.tdsoftware.moviesharing.ui.loading.LoadingActivity

/**
 * BaseActivity for all Activities in this application
 */
abstract class BaseActivity : AppCompatActivity() {

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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("PID",android.os.Process.myPid().toString())
    }
}
