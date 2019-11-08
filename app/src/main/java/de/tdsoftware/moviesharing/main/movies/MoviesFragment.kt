package de.tdsoftware.moviesharing.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.BaseFragment
import de.tdsoftware.moviesharing.R

class MoviesFragment : BaseFragment() {

    private lateinit var mainView: MoviesFragmentView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies,container,false) as MoviesFragmentView
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainView.setUpViewPager(childFragmentManager)
    }

    companion object {

        @JvmStatic
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

}
