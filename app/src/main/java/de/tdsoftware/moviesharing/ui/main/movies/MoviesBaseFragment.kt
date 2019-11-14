package de.tdsoftware.moviesharing.ui.main.movies

import android.os.Bundle
import android.view.View
import de.tdsoftware.moviesharing.ui.main.MainActivityBaseFragment
import de.tdsoftware.moviesharing.util.RecyclerUpdateEvent
import de.tdsoftware.moviesharing.util.Repository
import org.greenrobot.eventbus.Subscribe

//TODO: rename
abstract class MoviesBaseFragment: MainActivityBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistListForAdapter = Repository.playlistList
    }

    @Subscribe
    override fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent) {
        playlistListForAdapter = Repository.playlistList
    }
}