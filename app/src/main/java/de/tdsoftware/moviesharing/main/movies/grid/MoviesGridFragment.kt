package de.tdsoftware.moviesharing.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.main.movies.grid.MoviesGridFragmentView

class MoviesGridFragment : MoviesViewFragment(){

    private lateinit var mainView: MoviesGridFragmentView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies_grid,container,false) as MoviesGridFragmentView
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesGridFragment {
            return MoviesGridFragment()
        }
    }
}