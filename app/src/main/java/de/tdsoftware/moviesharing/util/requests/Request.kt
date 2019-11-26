package de.tdsoftware.moviesharing.util.requests

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.YouTubeApiResponse
import de.tdsoftware.moviesharing.util.Result
import kotlinx.coroutines.*
import okhttp3.*
import java.lang.Exception

/**
 * parent-class for all requests
 */
abstract class Request(private val callback: (Result<YouTubeApiResponse>) -> Unit) : CoroutineScope {

    // region public Types
    companion object {
        // deserialization instance
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        // API-Key for YouTube-API
        const val API_KEY = "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU"
    }

    // endregion

    // region properties

    private val job = Job()
    override val coroutineContext = Dispatchers.Default + job

    // enregion

    // region public API

    /**
     * Method that builds together the request-URL
     */
    abstract fun buildRequestUrl(): HttpUrl

    /**
     * Method that deserializes the successful response
     */
    abstract fun deserializeResponse(response: Response)

    /**
     * Method that "starts" the fetch for this request
     */
    fun fetch(){
        launch {
            checkResult(fetchFromApi(buildRequestUrl()))
        }
    }

    // endregion

    // region private API

    /**
     * Method that checks if response was successful
     */
    private fun checkResponse(response: Response){
        if(response.isSuccessful){
            deserializeResponse(response)
        }else{
            callback(Result.Error(
                400,
                "Error-Code from API while fetching playlists: " + response.code().toString()))
        }
    }

    /**
     * Method that checks if a response was obtained or not
     */
    private fun checkResult(result: Result<Response>){
        when (result) {
            is Result.Success -> {
                val response = result.data
                checkResponse(response)
            }
            is Result.Error -> {
                callback(result)
            }
        }
    }

    /**
     * Method that sends a GET-Request by using a HttpUrl & OkHttp3
     */
    private fun fetchFromApi(url: HttpUrl): Result<Response> {
        return try {
            val httpClient = OkHttpClient.Builder().build()
            val request = okhttp3.Request.Builder().url(url).build()
            val result = httpClient.newCall(request).execute()
            Result.Success(result)
        } catch (exception: Exception) {
            Result.Error(
                100,
                "Error connecting/Timeout. Check your connection!"
            )
        }
    }

    // endregion
}