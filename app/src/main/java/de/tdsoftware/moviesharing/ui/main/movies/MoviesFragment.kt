package de.tdsoftware.moviesharing.ui.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.R

class MoviesFragment : BaseFragment() {

    private lateinit var mainView: MoviesFragmentView
    private lateinit var moviesViewPagerAdapter: MoviesViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies,container,false) as MoviesFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMainView()
    }

    private fun setUpMainView(){
        moviesViewPagerAdapter = MoviesViewPagerAdapter(childFragmentManager)
        mainView.changeViewPagerAdapter(moviesViewPagerAdapter)
    }

    companion object {
        @JvmStatic
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

}
