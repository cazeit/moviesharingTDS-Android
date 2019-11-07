package de.tdsoftware.moviesharing.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.BaseFragment
import de.tdsoftware.moviesharing.R

class FavoritesFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    companion object{

        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }
}