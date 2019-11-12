package de.tdsoftware.moviesharing.ui.main.movies.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.Sample
import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragment
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistGridAdapter

class MoviesGridFragment : MoviesBaseFragment(){

    private lateinit var mainView: MoviesGridFragmentView

    private val playlistRecyclerAdapter: PlaylistGridAdapter
        get() {
            return playlistAdapter as PlaylistGridAdapter
        }

    override fun createPlayListAdapter(playlistList: ArrayList<PlaylistApp>): PlaylistBaseAdapter {
        return PlaylistGridAdapter(playlistList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies_grid,container,false) as MoviesGridFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistList = Sample.playlistSampleList
        setUpMainView()
    }

    private fun setUpMainView(){
        mainView.changePlaylistRecyclerAdapter(playlistRecyclerAdapter)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesGridFragment {
            return MoviesGridFragment()
        }
    }
}