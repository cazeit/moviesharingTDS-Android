package de.tdsoftware.moviesharing.main.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabViewAdapter(val fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager){

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return MoviesGridFragment.newInstance()
            }
            1 -> {
                return MoviesListFragment.newInstance()
            }
        }
        return MoviesListFragment.newInstance()
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