package de.tdsoftware.moviesharing.data.models


class Playlist(val id: String, val title: String?, var movieList: ArrayList<Movie>) {

    fun changeVideoList(movieListNew: ArrayList<Movie>){
        movieList = movieListNew
    }
}