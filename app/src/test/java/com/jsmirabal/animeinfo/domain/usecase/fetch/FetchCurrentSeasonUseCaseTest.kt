package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyDomainSeasonAnimesSuccess
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
class FetchCurrentSeasonUseCaseTest {
    private val repository = mockk<AnimeRepository>()
    private val testScope = TestCoroutineScope()
    private val useCase = FetchCurrentSeasonUseCase(repository, testScope)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun run() = runBlockingTest {
        TestLogger.given("AnimeRepository returns mocked data")
        coEvery { repository.fetchCurrentSeason() } returns dummyDomainSeasonAnimesSuccess

        TestLogger.whenever("FetchCurrentSeasonUseCase is executed")
        val result = useCase.run(Unit)

        TestLogger.then("Validate that the expected data is returned")
        result shouldEqual dummyDomainSeasonAnimesSuccess

        TestLogger.then("Validate AnimeRepository#fetchCurrentSeason() was called")
        coVerify { repository.fetchCurrentSeason() }

        TestLogger.finally("Validate every method called from AnimeRepository was verified")
        confirmVerified(repository)
    }
}
