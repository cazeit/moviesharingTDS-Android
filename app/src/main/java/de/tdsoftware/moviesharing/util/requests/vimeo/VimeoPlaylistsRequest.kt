package de.tdsoftware.moviesharing.util.requests.vimeo

import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.data.helper.vimeo.playlist.VimeoPlaylistsResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class VimeoPlaylistsRequest(private var pageString: String = "?page=1", private val callback: (Result<ApiResponse>) -> Unit) : VimeoRequest(callback) {

    override fun buildRequestUrl(): HttpUrl {
        if(pageString.isEmpty()) {
            pageString = "?page=1"
        }
        val page = pageString.substringAfterLast("?page=").substringBefore("&")
        return HttpUrl.Builder().scheme("https").host("api.vimeo.com").addPathSegments("me/albums")
            .addQueryParameter("page", page)
            .build()
    }

    override fun deserializeResponse(response: Response) {
        val playlistString = response.body()?.string()
        playlistString?.let {
            val jsonAdapter = moshi.adapter(VimeoPlaylistsResponse::class.java)
            val playlistResponse = jsonAdapter.fromJson(playlistString)
            if (playlistResponse != null) {
                callback(Result.Success(playlistResponse))
            }
        }
            ?: callback(Result.Error(150, "Error reading Server-Response."))
    }
}