package com.jsmirabal.animeinfo.domain.usecase

import com.jsmirabal.animeinfo.data.PAGE_NUMBER
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyTopAiringAnimes
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class FetchTopAnimesUseCaseTest {
    private val repository = mockk<AnimeRepository>()
    private val testScope = TestCoroutineScope()
    private val useCase = FetchTopAnimesUseCase(repository, testScope)

    @Test
    fun run() = runBlockingTest {
        TestLogger.given("AnimeRepository returns mocked data")
        coEvery { repository.fetchTopAiringAnimes(PAGE_NUMBER) } returns dummyTopAiringAnimes

        TestLogger.whenever("FetchTopAnimesUseCase is executed")
        val result = useCase.run(PAGE_NUMBER)

        TestLogger.then("Validate that the expected data is returned")
        result shouldEqual dummyTopAiringAnimes

        TestLogger.then("Validate AnimeRepository#fetchTopItems() was called")
        coVerify { repository.fetchTopAiringAnimes(PAGE_NUMBER) }

        TestLogger.finally("Validate every method called from AnimeRepository was verified")
        confirmVerified(repository)
    }
}
