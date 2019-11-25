package de.tdsoftware.moviesharing.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import de.tdsoftware.moviesharing.util.Notification
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * BaseFragment for all Fragments in this application
 */
abstract class BaseFragment : Fragment() {

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onNotification(notification: Notification) {
        when(notification) {
            is Notification.NetworkErrorEvent -> {
                view?.let {
                    Snackbar.make(
                        it,
                        "Error Nr. " + notification.code + ": " + notification.message,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
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
}
