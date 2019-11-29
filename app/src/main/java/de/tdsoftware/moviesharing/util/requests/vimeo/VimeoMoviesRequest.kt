package de.tdsoftware.moviesharing.util.requests.vimeo

import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.HttpUrl
import okhttp3.Response

class VimeoMoviesRequest(private val playlistId: String, private val pageNumber: String = "1", private val callback: (Result<ApiResponse>) -> Unit) : VimeoRequest(callback) {

    override fun buildRequestUrl(): HttpUrl {
        return HttpUrl.Builder().addPathSegments("me/albums/$playlistId/videos").build()
    }

    override fun deserializeResponse(response: Response) {
        // TODO: vimeo deserialization
    }
}