package de.tdsoftware.moviesharing.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class Request: CoroutineScope {

    private val job = Job()
    override val coroutineContext = Dispatchers.Default + job
}