package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist

sealed class Notification{
    class NetworkErrorEvent(val code: Int, val message: String): Notification()
    class PlaylistChangedEvent(val playlistList: ArrayList<Playlist>): Notification()
    class FavoriteChangedEvent(val favoriteList: ArrayList<Movie>): Notification()
}