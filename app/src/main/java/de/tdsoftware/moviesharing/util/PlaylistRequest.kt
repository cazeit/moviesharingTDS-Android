package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Playlist
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaylistRequest(private val callback: (Result<ArrayList<Playlist>>) -> Unit): Request() {

    private val playlistList = ArrayList<Playlist>()
    private var pageToken: String = ""

    override fun fetch(){
        launch {
            NetworkManager.fetchPlaylistListFromUser(pageToken) {
                when (it) {
                    is Result.Success -> {
                        val playlistResponse = it.data
                        playlistList.addAll(mapToPlaylists(playlistResponse))
                        if(playlistResponse.nextPageToken != null){
                            pageToken = playlistResponse.nextPageToken
                            fetch()
                        }else {
                            callback(Result.Success(playlistList))
                            super.onFetched()
                        }
                    }
                    is Result.Error -> {
                        callback(it)
                        super.onFetched()
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