package de.tdsoftware.moviesharing.ui.main.movies.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistGridAdapter

class MoviesGridFragment : BaseFragment(){

    private lateinit var mainView: MoviesGridFragmentView
    private lateinit var playlistRecyclerAdapter: PlaylistGridAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies_grid,container,false) as MoviesGridFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMainView()
    }

    private fun setUpMainView(){
        playlistRecyclerAdapter = PlaylistGridAdapter(this,2)
        mainView.changePlaylistRecyclerAdapter(playlistRecyclerAdapter)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesGridFragment {
            return MoviesGridFragment()
        }
    }
}