package com.jsmirabal.animeinfo.domain.usecase

import com.jsmirabal.animeinfo.TestDispatcherExtension
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import io.mockk.clearAllMocks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@ExtendWith(TestDispatcherExtension::class)
internal class UseCaseTest {
    private val useCaseResult = "result"
    private val testDispatcher = Dispatchers.Main
    private val scope = object : CoroutineScope{
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + Job()
    }
    private val useCase = object : UseCase<Long, String>(scope, testDispatcher) {
        override suspend fun run(duration: Long): String {
            return useCaseResult
        }
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun runAsync() {
        // Figuring out how to test this
        runBlocking {
            useCase.runAsync(2000) { result ->
                result shouldEqual useCaseResult
                // Even if an assert fails, there is no error thrown -.-
                assert(false)
            }
        }
    }

    @Test
    fun cancel() {
        // Figuring out how to test this
    }
}
