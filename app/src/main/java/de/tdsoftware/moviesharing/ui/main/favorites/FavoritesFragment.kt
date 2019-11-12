package de.tdsoftware.moviesharing.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.Sample
import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.ui.main.movies.MoviesBaseFragment
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.ui.main.movies.adapter.PlaylistFavoriteAdapter

class FavoritesFragment: MoviesBaseFragment(){

    private val favoritePlaylistRecyclerAdapter: PlaylistFavoriteAdapter
        get(){
            return playlistAdapter as PlaylistFavoriteAdapter
        }

    private lateinit var mainView: FavoritesFragmentView

    override fun createPlayListAdapter(playlistList: ArrayList<PlaylistApp>): PlaylistBaseAdapter {
        return PlaylistFavoriteAdapter(playlistList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_favorites, container, false) as FavoritesFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistList = arrayListOf(Sample.playlistFavorite)
        setupMainView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainView.changeFavoriteRecyclerAdapter(favoritePlaylistRecyclerAdapter)
    }

    private fun setupMainView(){
        mainView.viewListener = object : FavoritesFragmentView.Listener{
            override fun onQueryChange(newText: String) {
                if(newText.isNotEmpty()){
                    //TODO(update adapter with new data
                }
            }
        }
    }

    companion object{

        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }
}