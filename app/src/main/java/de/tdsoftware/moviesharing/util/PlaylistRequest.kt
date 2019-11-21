package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Playlist
import kotlinx.coroutines.launch

class PlaylistRequest(private val pageToken: String = "", private val callback: (Result<ArrayList<Playlist>>) -> Unit): Request() {

    init{
        fetch()
    }

    fun fetch(){
        launch {
            NetworkManager.fetchPlaylistListFromUser(pageToken) {
                when (it) {
                    is Result.Success -> {
                        val playlistResponse = it.data
                        callback(Result.Success(mapToPlaylists(playlistResponse)))
                    }
                    is Result.Error -> {
                        callback(it)
                    }
                }
            }
        }
    }

    private fun mapToPlaylists(playlistResponse: PlaylistResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for (responseItem in playlistResponse.items) {
            returnList.add(Playlist(responseItem.id, responseItem.snippet.title, ArrayList()))
        }
        return returnList
    }
}