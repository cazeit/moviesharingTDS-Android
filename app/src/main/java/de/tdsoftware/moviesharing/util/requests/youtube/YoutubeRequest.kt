package de.tdsoftware.moviesharing.util.requests.youtube

import de.tdsoftware.moviesharing.data.serialization.ApiResponse
import de.tdsoftware.moviesharing.util.Result
import de.tdsoftware.moviesharing.util.requests.Request
import okhttp3.Headers

abstract class YoutubeRequest(callback: (Result<ApiResponse>) -> Unit) : Request(callback) {

    companion object {
        const val API_KEY = "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU"
        const val CHANNEL_ID = "UCPppOIczZfCCoqAwRLc4T0A"
    }


    /**
     * no headers needed so far for youtube-api
     */
    override fun buildRequestHeaders(): Headers {
        return Headers.Builder().build()
    }
}