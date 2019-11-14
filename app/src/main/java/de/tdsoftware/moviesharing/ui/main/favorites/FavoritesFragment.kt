package de.tdsoftware.moviesharing.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.PlaylistApp
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

    private var favoriteVideoPlaylistListFiltered = ArrayList<PlaylistApp>(arrayListOf(PlaylistApp("filtered_favorites", "Filtered Favorites", arrayListOf())))

    private var favoritePlaylistFull: PlaylistApp
        get(){
            return Repository.favoritePlaylistList[0]
        }
        set(value){
            // here we do not want to access Repository
        }

    private var favoritePlaylistFiltered: PlaylistApp
        get(){
            return favoriteVideoPlaylistListFiltered[0]
        }
        set(value){
            favoriteVideoPlaylistListFiltered[0] = value
        }


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

        playlistListForAdapter = Repository.favoritePlaylistList

        setUpMainView()
    }

    private fun setUpMainView(){
        mainView.changePlaylistRecyclerAdapter(favoritePlaylistRecyclerAdapter)
        mainView.viewListener = object : FavoritesFragmentView.Listener{
            override fun onQueryChange(newText: String) {
                favoritePlaylistFiltered.videoList = ArrayList()
                if(newText.isEmpty()){
                    playlistListForAdapter = Repository.favoritePlaylistList
                }else{
                    for(video in favoritePlaylistFull.videoList){
                        if(video.title.contains(newText, true)){
                            favoritePlaylistFiltered.videoList.add(video)
                        }
                    }
                    playlistListForAdapter = favoriteVideoPlaylistListFiltered
                }
            }
        }
    }

    @Subscribe
    override fun onRecyclerUpdateEvent(recyclerUpdateEvent: RecyclerUpdateEvent) {
        playlistListForAdapter = Repository.favoritePlaylistList
    }


    companion object{

        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }
}