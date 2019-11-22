package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.helper.movie.MovieResponse
import de.tdsoftware.moviesharing.data.models.Movie
import kotlinx.coroutines.launch


class MoviesRequest(private val playlistId: String, private val callback: (Result<ArrayList<Movie>>) -> Unit): Request() {

    private val movieList = ArrayList<Movie>()
    private var pageToken: String = ""

    override fun fetch(){
        launch {
            NetworkManager.fetchMoviesFromPlaylist(playlistId, pageToken) {
                when (it) {
                    is Result.Success -> {
                        val movieResponse = it.data
                        movieList.addAll(mapToMovies(movieResponse))
                        if(movieResponse.nextPageToken != null){
                            pageToken = movieResponse.nextPageToken
                            fetch()
                        }else {
                            callback(Result.Success(movieList))
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

}