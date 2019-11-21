package de.tdsoftware.moviesharing.ui.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.adapter.MoviesViewPagerAdapter

/**
 * Fragment inside MainActivity, that sets up the viewpageradapter and connects it to the viewpager
 */
class MoviesFragment : BaseFragment() {

    // region public types
    companion object {
        @JvmStatic
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }
    // endregion

    // region properties

    private lateinit var mainView: MoviesFragmentView

    private lateinit var moviesViewPagerAdapter: MoviesViewPagerAdapter

    // endregion

    // region lifecycle callbacks

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_movies,container,false) as MoviesFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMainView()
    }

    // endregion

    // region private API

    private fun setUpMainView() {
        moviesViewPagerAdapter =
            MoviesViewPagerAdapter(childFragmentManager)
        mainView.changeViewPagerAdapter(moviesViewPagerAdapter)
    }

    // endregion
}
