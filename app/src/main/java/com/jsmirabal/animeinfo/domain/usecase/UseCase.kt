package com.jsmirabal.animeinfo.domain.usecase

import kotlinx.coroutines.*

abstract class UseCase <in T, out U> (
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var job: Job? = null

    abstract suspend fun run(param: T): U

    suspend fun runAsync(param: T, onResult: suspend (U) -> Unit) {
        cancel()
        job = scope.launch(dispatcher) {
            onResult(run(param))
        }
    }

    fun cancel() {
        job?.cancel()
    }
}