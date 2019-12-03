package de.tdsoftware.moviesharing.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import de.tdsoftware.moviesharing.util.Notification
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * BaseFragment for all Fragments in this application
 */
abstract class BaseFragment : Fragment() {

    // region EventBus

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onNotification(notification: Notification) {
        // design decision -> let all fragments subscribe -> open for common subscribe-use-case
    }

    // endregion

    // region lifecycle callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    // endregion
}