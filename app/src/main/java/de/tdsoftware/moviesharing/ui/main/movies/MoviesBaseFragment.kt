package de.tdsoftware.moviesharing.ui.main.movies

import android.os.Bundle
import android.view.View
import de.tdsoftware.moviesharing.ui.main.MainActivityBaseFragment
import de.tdsoftware.moviesharing.util.MoviesManager
import de.tdsoftware.moviesharing.util.Notification

/**
 * BaseFragment for MoviesGridFragment and MoviesListFragment, inheriting itself from MainActivityBaseFragment
 */

abstract class MoviesBaseFragment : MainActivityBaseFragment() {

    // region EventBus

    override fun onNotification(notification: Notification) {
        super.onNotification(notification)
        when (notification) {
            is Notification.PlaylistChangedEvent -> {
                playlistListInAdapter = notification.playlistList
            }
        }
    }

    // endregion

    // region lifecycle callback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistListInAdapter = MoviesManager.playlistList
    }

    // endregion
}