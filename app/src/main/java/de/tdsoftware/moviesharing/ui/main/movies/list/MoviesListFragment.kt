package de.tdsoftware.moviesharing.ui.main.movies.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter

class MoviesListFragment : MoviesBaseFragment() {

    private lateinit var mainView: MoviesListFragmentView

    private val playlistRecyclerAdapter: PlaylistListAdapter
        get(){
            return playlistAdapter as PlaylistListAdapter
        }


    override fun createPlayListAdapter(): PlaylistBaseAdapter {
        return PlaylistListAdapter()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies_list,container,false) as MoviesListFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMainView()
    }


    private fun setUpMainView(){
        mainView.changePlaylistRecyclerAdapter(playlistRecyclerAdapter)
    }


    companion object {

        @JvmStatic
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }
}