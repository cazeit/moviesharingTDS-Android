package de.tdsoftware.moviesharing.data.models

import java.io.Serializable

data class VideoApp(val id: String,
                    val title: String,
                    val description: String,
                    val secondaryText: String,
                    var rating: Float,
                    var isFavorite: Boolean,
                    var imagePath: String): Serializable