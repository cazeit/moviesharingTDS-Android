package de.tdsoftware.moviesharing.util

import android.content.SharedPreferences
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
import org.greenrobot.eventbus.EventBus

/**
 * Singleton that handles everything around Movies -> API for Movies
 */
object MoviesManager {

    // properties

    lateinit var playlistList: ArrayList<Playlist>
    val favoritePlaylistList = ArrayList<Playlist>()

    var favoritePlaylist: Playlist
        get() {
            return favoritePlaylistList[0]
        }
        set(value) {
            favoritePlaylistList[0] = value
        }
    private lateinit var sharedPreferences: SharedPreferences

    private var successFetching = true

    // endregion

    // constructors
    /**
     * creating a playlist for favorites at runtime
     */
    init {
        val favoriteList = Playlist("fav001", "Favorite", ArrayList())
        favoritePlaylistList.add(favoriteList)
    }
    // endregion


    // public API
    /**
     * This needs to be called in advance of fetching playlists
     */
    fun setUpMoviesManager(sharedPref: SharedPreferences) {
        sharedPreferences = sharedPref
    }

    fun fetchPlaylistListWithMovies() {
        PlaylistRequest{
            when(it){
                is Result.Success -> {
                    fetchMoviesForPlaylistList(it.data)
                }
                is Result.Error -> {
                    EventBus.getDefault().post(Notification.NetworkErrorEvent(it.code, it.message))
                }
            }
        }
    }

    fun updateFavorites(movie: Movie) {
        for (currentMovie in favoritePlaylist.movieList) {
            if (currentMovie.id == movie.id) {
                favoritePlaylist.movieList.remove(currentMovie)
                EventBus.getDefault().post(Notification.PlaylistChangedEvent(playlistList))
                EventBus.getDefault().post(Notification.FavoriteChangedEvent(favoritePlaylist.movieList))
                return
            }
        }
        favoritePlaylist.movieList.add(movie)
        EventBus.getDefault().post(Notification.PlaylistChangedEvent(playlistList))
        EventBus.getDefault().post(Notification.FavoriteChangedEvent(favoritePlaylist.movieList))
    }

    // private API

    private fun fetchMoviesForPlaylistList(emptyPlaylistList: ArrayList<Playlist>){
        for(playlist in emptyPlaylistList) {
            MoviesRequest(playlist.id) {
                when(it){
                    is Result.Success -> {
                        playlist.movieList = it.data
                        if(playlist.id == emptyPlaylistList.last().id){
                            playlistList = emptyPlaylistList
                            initializeFavorites()
                            EventBus.getDefault().post(Notification.PlaylistChangedEvent(emptyPlaylistList))
                        }
                    }
                    is Result.Error -> {
                        // remove all open movie-requests..
                        Request.unregisterAll(MoviesRequest::class.java)
                        EventBus.getDefault().post(Notification.NetworkErrorEvent(it.code, it.message))
                    }
                }
            }
        }
    }

    private fun initializeFavorites() {
        for (playlist in playlistList) {
            for (movie in playlist.movieList) {
                if (sharedPreferences.getBoolean(movie.id + "_favorite", false)) {
                    favoritePlaylist.movieList.add(movie)
                }
            }
        }
    }
    // endregion
}