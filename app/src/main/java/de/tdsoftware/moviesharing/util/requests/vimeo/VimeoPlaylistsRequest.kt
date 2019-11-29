package de.tdsoftware.moviesharing.util.requests.vimeo

import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class VimeoPlaylistsRequest(private val pageNumber: String = "1", private val callback: (Result<ApiResponse>) -> Unit) : VimeoRequest(callback) {

    override fun buildRequestUrl(): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("api.vimeo.com").addPathSegments("me/albums")
            .build()
    }

    override fun deserializeResponse(response: Response) {
        println(response.body()?.string())
    }
}