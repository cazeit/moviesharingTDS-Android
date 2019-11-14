package de.tdsoftware.moviesharing.util

import de.tdsoftware.moviesharing.data.models.Movie

class FavoriteUpdateEvent{
    lateinit var movie: Movie
}