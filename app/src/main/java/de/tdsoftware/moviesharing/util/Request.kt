package de.tdsoftware.moviesharing.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class Request: CoroutineScope {

    companion object{
        private var isRequesting = false

        var requestQueue = ArrayList<Request>()

        fun register(request: Request){
            requestQueue.add(request)
        }

        fun unregister(request: Request){
            requestQueue.remove(request)
        }

        fun <T>unregisterAll(type: Class<out T>){
            val newRequestQueue = ArrayList<Request>()
            for(request in requestQueue){
                if(request::class.java.simpleName != type.simpleName){
                    newRequestQueue.add(request)
                }
            }
            requestQueue = newRequestQueue
        }
    }

    private val job = Job()
    override val coroutineContext = Dispatchers.Default + job

    init{
        onRequestAdded()
    }

    abstract fun fetch()

    private fun onRequestAdded(){
        register(this)
        if(!isRequesting){
            isRequesting = true
            this.fetch()
        }
    }

    /**
     * this needs to be called from child-class when done fetching..
     */
    fun onFetched(){
        isRequesting = false
        unregister(this)
        if(requestQueue.isNotEmpty()){
            requestQueue.first().fetch()
        }
    }
}