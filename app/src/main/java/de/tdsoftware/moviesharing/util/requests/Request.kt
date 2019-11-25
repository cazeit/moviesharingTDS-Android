package de.tdsoftware.moviesharing.util.requests

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.tdsoftware.moviesharing.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.*
import java.lang.Exception

abstract class Request(private val registerAsFirst: Boolean = false) : CoroutineScope {

    companion object {
        var requestQueue = ArrayList<Request>()
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        const val API_KEY = "AIzaSyC-rueCbrPcU1ZZAnoozj1FC1dVQLsiVmU"
        var isRequesting = false

        fun register(request: Request) {
            if (request.registerAsFirst) {
                requestQueue.add(0, request)
            } else {
                requestQueue.add(request)
            }
        }

        fun unregister(request: Request) {
            requestQueue.remove(request)
        }

        // unregister all pending requests from a kind..
        fun <T> unregisterAll(type: Class<out T>) {
            val newRequestQueue = ArrayList<Request>()
            for (request in requestQueue) {
                if (request::class.java != type) {
                    newRequestQueue.add(request)
                }
            }
            requestQueue = newRequestQueue
        }
    }

    private val job = Job()
    override val coroutineContext = Dispatchers.Default + job

    init {
        onRequestAdded()
    }

    abstract fun fetch()

    private fun onRequestAdded() {
        register(this)
        if (!isRequesting) {
            isRequesting = true
            fetch()
        }
    }

    protected fun fetchFromApi(url: HttpUrl): Result<Response> {
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

    /**
     * This needs to be called in child-class when done fetching..
     */
    protected fun onFetched() {
        isRequesting = false
        unregister(this)
        if (requestQueue.isNotEmpty()) {
            isRequesting = true
            requestQueue.first().fetch()
        }
    }
}