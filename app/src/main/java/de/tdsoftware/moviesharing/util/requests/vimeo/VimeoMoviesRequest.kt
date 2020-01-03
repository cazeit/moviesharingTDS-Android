package de.tdsoftware.moviesharing.util.requests.vimeo

import de.tdsoftware.moviesharing.data.serialization.ApiResponse
import de.tdsoftware.moviesharing.data.serialization.vimeo.movie.VimeoMoviesResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class VimeoMoviesRequest(
    private val playlistId: String,
    private var pageString: String = "?page=1",
    private val callback: (Result<ApiResponse>) -> Unit
) : VimeoRequest(callback) {

    override fun buildRequestUrl(): HttpUrl {
        if (pageString.isEmpty()) {
            pageString = "?page=1"
        }
        val page = pageString.substringAfterLast("?page=").substringBefore("&")
        return HttpUrl.Builder().scheme("https").host("api.vimeo.com")
            .addPathSegments("me/albums/$playlistId/videos").addQueryParameter("page", page)
            .addQueryParameter("per_page", "50").build()
    }

    override fun deserializeResponse(response: Response) {
        val movieString = response.body()?.string()
        movieString?.let {
            val jsonAdapter = moshi.adapter(VimeoMoviesResponse::class.java)
            val movieResponse = jsonAdapter.fromJson(movieString)
            if (movieResponse != null) {
                callback(Result.Success(movieResponse))
            }
        }
            ?: callback(Result.Error(150, "Error reading Server-Response."))
    }
}