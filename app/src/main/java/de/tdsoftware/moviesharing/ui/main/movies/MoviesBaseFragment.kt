package de.tdsoftware.moviesharing.ui.main.movies

import android.os.Bundle
import android.view.View
import de.tdsoftware.moviesharing.ui.main.MainActivityBaseFragment
import de.tdsoftware.moviesharing.util.RecyclerUpdateEvent
import de.tdsoftware.moviesharing.util.Repository
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class MoviesBaseFragment: MainActivityBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistListAdapter = Repository.playlistList
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent) {
        playlistListAdapter = Repository.playlistList
    }
}