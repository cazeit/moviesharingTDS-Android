package de.tdsoftware.moviesharing.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R

class MoviesListFragment : MoviesViewFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list,container,false)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }
}