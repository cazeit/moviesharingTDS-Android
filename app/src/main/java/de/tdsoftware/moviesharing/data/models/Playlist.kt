package de.tdsoftware.moviesharing.data.models

import com.squareup.moshi.JsonClass

/**
 * Model for a playlist
 */
@JsonClass(generateAdapter = true)
data class Playlist(val id: String, val title: String?, var movieList: ArrayList<Movie>)