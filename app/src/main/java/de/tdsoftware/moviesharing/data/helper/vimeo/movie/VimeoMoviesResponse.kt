package de.tdsoftware.moviesharing.data.helper.vimeo.movie


import com.squareup.moshi.Json
import de.tdsoftware.moviesharing.data.helper.MoviesApiResponse

data class VimeoMoviesResponse(
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
) : MoviesApiResponse(paging.next)