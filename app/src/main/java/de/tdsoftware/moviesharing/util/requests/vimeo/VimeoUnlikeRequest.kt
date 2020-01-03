package de.tdsoftware.moviesharing.util.requests.vimeo

import de.tdsoftware.moviesharing.data.serialization.ApiResponse
import de.tdsoftware.moviesharing.data.serialization.vimeo.favorite.VimeoFavoriteResponse
import de.tdsoftware.moviesharing.util.Result
import okhttp3.*
import java.lang.Exception

class VimeoUnlikeRequest(
    private val videoId: String,
    private val callback: (Result<ApiResponse>) -> Unit
) : VimeoRequest(callback) {

    override fun buildRequestUrl(): HttpUrl {
        return HttpUrl.Builder().scheme("https").host("api.vimeo.com")
            .addPathSegments("me/likes/$videoId").build()
    }

    override fun deserializeResponse(response: Response) {
        if (response.isSuccessful) {
            callback(Result.Success(VimeoFavoriteResponse(false)))
        } else {
            callback(Result.Error(444, "Error removing the video from favorites."))
        }
    }

    override fun fetchFromApi(url: HttpUrl, headers: Headers): Result<Response> {
        return try {
            val httpClient = OkHttpClient.Builder().build()
            val request = Request.Builder().headers(headers).url(url).delete().build()
            val response = httpClient.newCall(request).execute()
            return Result.Success(response)
        } catch (e: Exception) {
            Result.Error(100, "Error connecting/ Timeout. Check your connection!")
        }
    }
}
