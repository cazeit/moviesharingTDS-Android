package de.tdsoftware.moviesharing.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.BaseFragment
import de.tdsoftware.moviesharing.R

//TODO: This fragment has a viewPager, that is displaying grid and listview
class MoviesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }
}
