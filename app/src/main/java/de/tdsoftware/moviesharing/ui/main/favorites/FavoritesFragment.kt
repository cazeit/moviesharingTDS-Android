package de.tdsoftware.moviesharing.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.main.MainActivityBaseFragment
import de.tdsoftware.moviesharing.ui.main.adapter.PlaylistBaseAdapter
import de.tdsoftware.moviesharing.util.RecyclerUpdateEvent
import de.tdsoftware.moviesharing.util.Repository
import org.greenrobot.eventbus.Subscribe

class FavoritesFragment: MainActivityBaseFragment(){

    private val favoritePlaylistRecyclerAdapter: PlaylistFavoriteAdapter
        get(){
            return playlistAdapter as PlaylistFavoriteAdapter
        }

    private lateinit var filteredList: ArrayList<Movie>
    private lateinit var fullList: ArrayList<Movie>

    private lateinit var mainView: FavoritesFragmentView

    override fun createPlayListAdapter(): PlaylistBaseAdapter {
        return PlaylistFavoriteAdapter()
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

        fullList = Repository.favoritePlaylist.movieList
        playlistListAdapter = Repository.favoritePlaylistList

        setUpMainView()
    }

    private fun createFilteredPlaylistList(movieList: ArrayList<Movie>): ArrayList<Playlist> {
        val playlist = Playlist("favorites_filtered","Favorites",movieList)
        return arrayListOf(playlist)
    }

    private fun setUpMainView(){
        mainView.changePlaylistRecyclerAdapter(favoritePlaylistRecyclerAdapter)

        mainView.viewListener = object : FavoritesFragmentView.Listener{
            override fun onQueryChange(newText: String) {

                filteredList = filter(newText)
                playlistListAdapter = createFilteredPlaylistList(filteredList)
            }
        }

        if(fullList.isEmpty()){
            mainView.changeNoFavoritesTextViewVisibility(true)
        }else{
            mainView.changeNoFavoritesTextViewVisibility(false)
        }
    }

    @Subscribe
    override fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent) {
        //update fulllist
        fullList = Repository.favoritePlaylist.movieList
        //filter the list
        filteredList = filter(mainView.searchViewQuery)
        //set the playlist-list in the adapter by using the filteredList
        playlistListAdapter = createFilteredPlaylistList(filteredList)

        if (fullList.isEmpty()) {
            mainView.changeNoFavoritesTextViewVisibility(true)
        } else {
            mainView.changeNoFavoritesTextViewVisibility(false)
        }
    }


    private fun filter(query: String): ArrayList<Movie>{
        if(query.isEmpty()){
            return fullList
        }else{
            val retList = ArrayList<Movie>()
            for(movie in fullList){
                if(movie.title.contains(query, true)){
                    retList.add(movie)
                }
            }
            return retList
        }
    }

    companion object{

        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }
}