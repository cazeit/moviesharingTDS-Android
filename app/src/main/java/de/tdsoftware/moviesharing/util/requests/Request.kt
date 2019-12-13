package de.tdsoftware.moviesharing.util.requests

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.ApiResponse
import de.tdsoftware.moviesharing.util.Result
import kotlinx.coroutines.*
import okhttp3.*
import java.lang.Exception

/**
 * parent-class for all requests
 */
abstract class Request(private val callback: (Result<ApiResponse>) -> Unit) : CoroutineScope {

    // region public Types
    companion object {
        // deserialization instance
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
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
     * Method that builds together needed Headers
     */
    abstract fun buildRequestHeaders(): Headers

    /**
     * Method that deserializes the successful response
     */
    abstract fun deserializeResponse(response: Response)

    /**
     * Method that "starts" the fetch for this request
     */
    fun fetch(){
        launch {
            checkResult(fetchFromApi(buildRequestUrl(), buildRequestHeaders()))
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
     * Method that sends a GET-Request by using OkHttp3
     */
    protected open fun fetchFromApi(url: HttpUrl, headers: Headers): Result<Response> {
        return try {
            val httpClient = OkHttpClient.Builder().build()
            val request = okhttp3.Request.Builder().headers(headers).url(url).build()
            val response = httpClient.newCall(request).execute()
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(
                100,
                "Error connecting/Timeout. Check your connection!"
            )
        }
    }

    // endregion
}