package de.tdsoftware.moviesharing.data.helper.vimeo.playlist

import de.tdsoftware.moviesharing.data.helper.PlaylistsApiResponse


// TODO: create this from json response
data class VimeoPlaylistsResponse(private val someToken: String?) : PlaylistsApiResponse(someToken)