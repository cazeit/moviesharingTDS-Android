package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.helper.playlist.PlaylistResponse
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist
/**
 * Singleton, that handles all networking (API-calls) and
 */
object NetworkManager {

    // region properties

    private val playlistList by lazy {
        ArrayList<Playlist>()
    }

    private val movieList by lazy{
        ArrayList<Movie>()
    }

    // endregion

    // region public API

    fun fetchPlaylistList(pageToken: String = "", callback: (Result<ArrayList<Playlist>>) -> Unit){
        PlaylistRequest(pageToken){
            when(it){
                is Result.Success -> {
                    val playlistResponse = it.data
                    playlistList.addAll(mapToPlaylists(playlistResponse))
                    if(playlistResponse.nextPageToken != null){
                        fetchPlaylistList(playlistResponse.nextPageToken, callback)
                    }else{
                        callback(Result.Success(playlistList))
                        // give free memory after..
                        playlistList.clear()
                    }
                }
                is Result.Error -> {
                    callback(it)
                }
            }
        }
    }

    fun fetchMoviesFromPlaylist(playlist: Playlist, pageToken: String = "", callback: (Result<ArrayList<Movie>>) -> Unit){
        MoviesRequest(playlist.id, pageToken){
            when(it){
                is Result.Success -> {
                    val movieResponse = it.data
                    movieList.addAll(mapToMovies(it.data))
                    if(movieResponse.nextPageToken != null){
                        fetchMoviesFromPlaylist(playlist, movieResponse.nextPageToken, callback)
                    }else{
                        callback(Result.Success(movieList))
                        // give free memory after..
                        movieList.clear()
                    }
                }
                is Result.Error -> {
                    callback(it)
                }
            }
        }
    }

    private fun mapToMovies(movieResponse: MovieResponse): ArrayList<Movie> {
        val returnList = ArrayList<Movie>()
        for (responseItem in movieResponse.items) {
            val secondaryText =
                "added on: " + responseItem.snippet.publishedAt.substring(8, 10) + "." +
                        responseItem.snippet.publishedAt.substring(5, 7) + "." +
                        responseItem.snippet.publishedAt.substring(0, 4)
            val imageString = responseItem.snippet.thumbnails.high.url
            returnList.add(
                Movie(
                    responseItem.snippet.resourceId.videoId,
                    responseItem.snippet.title,
                    responseItem.snippet.description,
                    secondaryText,
                    imageString
                )
            )
        }
        return returnList
    }

    private fun mapToPlaylists(playlistResponse: PlaylistResponse): ArrayList<Playlist> {
        val returnList = ArrayList<Playlist>()
        for (responseItem in playlistResponse.items) {
            returnList.add(Playlist(responseItem.id, responseItem.snippet.title, ArrayList()))
        }
        return returnList
    }

    // endregion
}