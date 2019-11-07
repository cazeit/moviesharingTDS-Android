package de.tdsoftware.moviesharing.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R

class MoviesGridFragment : MoviesViewFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_grid,container,false)
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