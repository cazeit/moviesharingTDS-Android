package de.tdsoftware.moviesharing.ui.main.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import de.tdsoftware.moviesharing.ui.main.movies.grid.MoviesGridFragment
import de.tdsoftware.moviesharing.ui.main.movies.list.MoviesListFragment

class MoviesViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val moviesGridFragment: MoviesGridFragment by lazy{
        MoviesGridFragment.newInstance()
    }

    private val moviesListFragment: MoviesListFragment by lazy{
        MoviesListFragment.newInstance()
    }


    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return moviesGridFragment
            }
            1 -> {
                return moviesListFragment
            }
        }
        return moviesGridFragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {
                return "Grid"
            }
            1 -> {
                return "List"
            }
        }
        return ""
    }
}