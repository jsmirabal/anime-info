package com.jsmirabal.animeinfo.domain.usecase.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

abstract class UseCase<in T, out U>(
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var job: Job? = null

    abstract suspend fun run(param: T): U

    fun runAsync(param: T, onResult: suspend (U) -> Unit): Job = scope.launch(dispatcher) {
        onResult(run(param))
    }.apply { job = this }

    fun runAsync(param: T): Deferred<U> = scope.async(dispatcher) {
        run(param)
    }.apply { job = this }

    fun cancel() {
        job?.cancel()
    }
}
