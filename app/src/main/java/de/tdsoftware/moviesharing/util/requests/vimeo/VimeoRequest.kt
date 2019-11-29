package de.tdsoftware.moviesharing.util.requests.vimeo

import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.util.Result
import de.tdsoftware.moviesharing.util.requests.Request
import okhttp3.Headers

abstract class VimeoRequest(callback: (Result<ApiResponse>) -> Unit) : Request(callback) {

    companion object {
        const val ACCESS_TOKEN = "3c8503074f2a2333c953d4159d12ffb8"
    }

    // generated token in API command cell
    override fun buildRequestHeaders(): Headers {
        return Headers.Builder().add("Authorization", "bearer $ACCESS_TOKEN").build()
    }
}