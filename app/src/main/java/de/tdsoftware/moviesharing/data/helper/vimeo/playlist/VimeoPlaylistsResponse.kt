package de.tdsoftware.moviesharing.data.helper.vimeo.playlist


import com.squareup.moshi.Json
import de.tdsoftware.moviesharing.data.helper.PlaylistsApiResponse

data class VimeoPlaylistsResponse(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "paging")
    val paging: Paging,
    @Json(name = "per_page")
    val perPage: Int,
    @Json(name = "total")
    val total: Int
) : PlaylistsApiResponse(paging.next)