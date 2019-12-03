package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.data.models.Playlist

/**
 * Notification represents the data-type that is sent through EventBus.
 */
sealed class Notification {
    class NetworkErrorEvent(val code: Int, val message: String) : Notification()
    class PlaylistListChangedEvent(val playlistList: ArrayList<Playlist>) : Notification()
    class FavoritesRemovedFromListEvent(val favoriteList: ArrayList<Movie>, val removedIndex: Int = -1) :
        Notification()

    class FavoriteListChangedEvent(val favoriteList: ArrayList<Movie>) : Notification()
    class MovieLikeStatusChangedEvent(val isFavorite: Boolean): Notification()
}