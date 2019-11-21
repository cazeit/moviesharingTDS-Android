package de.tdsoftware.moviesharing.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Playlist(val id: String, val title: String?, var movieList: ArrayList<Movie>)