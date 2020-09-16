package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.data.PAGE_NUMBER
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyDomainTopAnimesResultSuccess
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class FetchTopUpcomingAnimesUseCaseTest {
    private val repository = mockk<AnimeRepository>()
    private val testScope = TestCoroutineScope()
    private val useCase = FetchTopUpcomingAnimesUseCase(repository, testScope)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun run() = runBlockingTest {
        TestLogger.given("AnimeRepository returns mocked data")
        coEvery { repository.fetchTopUpcomingAnimes(PAGE_NUMBER) } returns dummyDomainTopAnimesResultSuccess

        TestLogger.whenever("FetchTopUpcomingAnimesUseCase is executed")
        val result = useCase.run(PAGE_NUMBER)

        TestLogger.then("Validate that the expected data is returned")
        result shouldEqual dummyDomainTopAnimesResultSuccess

        TestLogger.then("Validate AnimeRepository#fetchTopItems() was called")
        coVerify { repository.fetchTopUpcomingAnimes(PAGE_NUMBER) }

        TestLogger.finally("Validate every method called from AnimeRepository was verified")
        confirmVerified(repository)
    }
}
