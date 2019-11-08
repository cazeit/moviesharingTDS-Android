package de.tdsoftware.moviesharing.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.movies.list.MoviesListFragmentView

class MoviesListFragment : MoviesViewFragment() {

    private lateinit var mainView: MoviesListFragmentView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies_list,container,false) as MoviesListFragmentView
        return mainView
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }
}