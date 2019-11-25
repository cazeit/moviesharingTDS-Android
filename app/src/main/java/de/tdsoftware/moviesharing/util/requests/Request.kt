package de.tdsoftware.moviesharing.util.requests

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.data.helper.YouTubeApiResponse
import de.tdsoftware.moviesharing.util.Result
import kotlinx.coroutines.*
import okhttp3.*
import java.lang.Exception

abstract class Request(private val callback: (Result<YouTubeApiResponse>) -> Unit) : CoroutineScope {

    companion object {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        const val API_KEY = "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU"
    }

    private val job = Job()
    override val coroutineContext = Dispatchers.Default + job

    abstract fun buildRequestUrl(): HttpUrl

    abstract fun deserialize(response: Response)

    fun checkResponse(response: Response){
        if(response.isSuccessful){
            deserialize(response)
        }else{
            callback(Result.Error(
                    400,
                "Error-Code from API while fetching playlists: " + response.code().toString()))
        }
    }

    fun fetch(){
        launch {
            checkResult(fetchFromApi(buildRequestUrl()))
        }
    }


    fun checkResult(result: Result<Response>){
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
}