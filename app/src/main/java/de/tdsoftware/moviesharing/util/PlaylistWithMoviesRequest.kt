package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.Playlist
import kotlinx.coroutines.launch

class PlaylistWithMoviesRequest(private val callback: (Result<ArrayList<Playlist>>) -> Unit): Request() {
    
    private var playlistCounter = 0

    init{
        fetch()
    }

    fun fetch() {
        launch {
            PlaylistRequest {
                when (it) {
                    is Result.Success -> {
                        fetchMoviesForPlaylistList(it.data)
                    }
                    is Result.Error -> {
                        callback(it)
                    }
                }
            }
        }
    }

    /**
     * the problem now is, that even if we have an error fetching, we still run it.. (so are we waiting for the callback and then start the new request?)
     * ->maybe set a flag in Request parent-class that we are waiting for something..
     */
    private fun fetchMoviesForPlaylistList(playlistList: ArrayList<Playlist>){
        for(playlist in playlistList){
            println("Running playlistFetchLoop")
            MoviesRequest(playlist.id){
                when(it){
                    is Result.Success -> {
                        playlist.movieList = it.data
                        if(checkPlaylistCounter(playlistList.size)){
                            callback(Result.Success(playlistList))
                        }
                    }
                    is Result.Error -> {
                        callback(it)
                    }
                }
            }
        }
    }


    private fun checkPlaylistCounter(playlistListSize: Int): Boolean{
        playlistCounter++
        return (playlistCounter == playlistListSize)
    }
}