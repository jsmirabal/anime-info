package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.data.ANIME_ID
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyDomainAnimeVideosResultSuccess
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
internal class FetchAnimeVideosUseCaseTest {
    private val repository = mockk<AnimeRepository>()
    private val testScope = TestCoroutineScope()
    private val useCase = FetchAnimeVideosUseCase(repository, testScope)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun run() = runBlockingTest {
        TestLogger.given("AnimeRepository returns mocked data")
        coEvery { repository.fetchAnimeVideos(ANIME_ID) } returns dummyDomainAnimeVideosResultSuccess

        TestLogger.whenever("FetchAnimeVideoUseCase is executed")
        val result = useCase.run(ANIME_ID)

        TestLogger.then("Validate that the expected data is returned")
        result shouldEqual dummyDomainAnimeVideosResultSuccess

        TestLogger.then("Validate AnimeRepository#fetchAnimeVideo() was called")
        coVerify { repository.fetchAnimeVideos(ANIME_ID) }

        TestLogger.finally("Validate every method called from AnimeRepository was verified")
        confirmVerified(repository)
    }
}
