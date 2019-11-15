package de.tdsoftware.moviesharing.data.models


data class Playlist(val id: String, val title: String?, var movieList: ArrayList<Movie>)