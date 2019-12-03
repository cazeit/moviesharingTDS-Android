package de.tdsoftware.moviesharing.data.helper.vimeo.favorite

import de.tdsoftware.moviesharing.data.helper.ApiResponse

data class VimeoFavoriteResponse(private var isFavorite: Boolean) : ApiResponse()