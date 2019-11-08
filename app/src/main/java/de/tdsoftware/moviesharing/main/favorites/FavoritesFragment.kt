package de.tdsoftware.moviesharing.main.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import de.tdsoftware.moviesharing.BaseFragment
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.video.VideoDetailsActivity

class FavoritesFragment: BaseFragment() {

    private lateinit var mainView: FavoritesFragmentView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_favorites, container, false) as FavoritesFragmentView
        return mainView
    }

    companion object{

        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }
}