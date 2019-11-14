package de.tdsoftware.moviesharing.data.models

import java.io.Serializable

data class Movie(val id: String,
                 val title: String,
                 val description: String,
                 val secondaryText: String,
                 var imagePath: String): Serializable