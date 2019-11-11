package de.tdsoftware.moviesharing.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.ui.main.movies.adapter.VideoListAdapter

class FavoritesFragment: BaseFragment(){

    // region private
    private lateinit var mainView: FavoritesFragmentView
    private lateinit var favoriteRecyclerAdapter: VideoListAdapter

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
        setupMainView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteRecyclerAdapter = VideoListAdapter(2)
        mainView.changeFavoriteRecyclerAdapter(favoriteRecyclerAdapter)
        favoriteRecyclerAdapter.listener = this
    }

    private fun setupMainView(){
        mainView.viewListener = object : FavoritesFragmentView.Listener{
            override fun onQueryChange(newText: String) {
                if(newText.isNotEmpty()){
                    //TODO(update adapter with new data
                }
            }

            override fun onQuerySubmit(query: String) {

            }
        }
    }

    companion object{

        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }
}