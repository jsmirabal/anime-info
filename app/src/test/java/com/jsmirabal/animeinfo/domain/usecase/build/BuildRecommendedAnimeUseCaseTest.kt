package com.jsmirabal.animeinfo.domain.usecase.build

import com.jsmirabal.animeinfo.data.*
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchAnimeDetailUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchAnimeVideosUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchTopAiringAnimesUseCase
import io.mockk.MockKMatcherScope
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class BuildRecommendedAnimeUseCaseTest {
    private val testScope = TestCoroutineScope()

    private val animeIdSlot = slot<(DomainTopAnimes) -> Int>()
    private val animeDetailSlot = slot<(DomainAnimeDetail) -> DomainAnimeDetail>()
    private val animeVideosSlot = slot<(DomainAnimeVideos) -> DomainAnimeVideos>()
    private val throwSlot = slot<(DomainLayerError) -> Exception>()

    private val topAiringAnimesUseCase = mockk<FetchTopAiringAnimesUseCase>()
    private val animeDetailUseCase = mockk<FetchAnimeDetailUseCase>()
    private val animeVideosUseCase = mockk<FetchAnimeVideosUseCase>()
    private val useCase = BuildRecommendedAnimeUseCase(
        topAiringAnimesUseCase,
        animeDetailUseCase,
        animeVideosUseCase,
        testScope
    )

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `fetch recommended anime then success`() = runBlockingTest {
        TestLogger.given("Dependee use cases return mocked data")
        coEvery { topAiringAnimesUseCase.run(any()) } returns dummyDomainTopAnimesResultSuccess
        coEvery { animeDetailUseCase.runAsync(any()).await() } returns dummyDomainAnimeDetailResultSuccess
        coEvery { animeVideosUseCase.runAsync(any()).await() } returns dummyDomainAnimeVideosResultSuccess
        coEvery { dummyDomainTopAnimes.topAnimes } returns emptyList()

        TestLogger.and("ResultWrapper returns mocked data")
        every { topAnimesSuccessCaptured() } returns 0
        every { animeDetailSuccessCaptured() } returns dummyAnimeDetailMapped
        every { animeVideosSuccessCaptured() } returns dummyAnimeVideosMapped

        TestLogger.whenever("FetchRecommendedAnimeUseCase is executed")
        val result = useCase.run(Unit)
        animeIdSlot.captured.invoke(dummyTopAnimesMapped)
        animeDetailSlot.captured.invoke(dummyAnimeDetailMapped)
        animeVideosSlot.captured.invoke(dummyAnimeVideosMapped)

        TestLogger.then("Validate that the expected data is returned")
        result.success()?.get() shouldEqual dummyRecommendedAnimeMapped

        TestLogger.then("Validate dependee use cases were called")
        coVerifyAll {
            topAiringAnimesUseCase.run(any())
            animeDetailUseCase.runAsync(any()).await()
            animeVideosUseCase.runAsync(any()).await()

            topAnimesSuccessCaptured()
            animeDetailSuccessCaptured()
            animeVideosSuccessCaptured()
        }

        TestLogger.finally("Validate every method called from dependee use cases were verified")
        confirmVerified(topAiringAnimesUseCase, animeDetailUseCase, animeDetailUseCase)
    }

    @Test
    fun `fetch recommended anime then anime id fails`() = runBlockingTest {
        TestLogger.given("Dependee use cases return mocked data")
        coEvery { topAiringAnimesUseCase.run(any()) } returns dummyDomainTopAnimesResultError

        TestLogger.and("ResultWrapper returns mocked data")
        every { topAnimesErrorCaptured() } throws dummyWrappedException

        TestLogger.whenever("FetchRecommendedAnimeUseCase is executed")
        val result = useCase.run(Unit)
        throwSlot.captured.invoke(dummyBusinessError)

        TestLogger.then("Validate that the expected data is returned")
        result.error()?.get() shouldEqual dummyBusinessError

        TestLogger.then("Validate dependee use cases were called")
        coVerifyAll {
            topAiringAnimesUseCase.run(any())
            topAnimesErrorCaptured()
        }

        TestLogger.finally("Validate every method called from dependee use cases were verified")
        confirmVerified(topAiringAnimesUseCase)
    }

    @Test
    fun `fetch recommended anime then anime detail fails`() = runBlockingTest {
        TestLogger.given("Dependee use cases return mocked data")
        coEvery { topAiringAnimesUseCase.run(any()) } returns dummyDomainTopAnimesResultSuccess
        coEvery { animeDetailUseCase.runAsync(any()).await() } returns dummyDomainAnimeDetailResultError

        TestLogger.and("ResultWrapper returns mocked data")
        every { topAnimesSuccessCaptured() } returns ANIME_ID.toInt()
        every { animeDetailErrorCaptured() } throws dummyWrappedException

        TestLogger.whenever("FetchRecommendedAnimeUseCase is executed")
        val result = useCase.run(Unit)
        animeIdSlot.captured.invoke(dummyTopAnimesMapped)
        throwSlot.captured.invoke(dummyBusinessError)

        TestLogger.then("Validate that the expected data is returned")
        result.error()?.get() shouldEqual dummyBusinessError

        TestLogger.then("Validate dependee use cases were called")
        coVerifyAll {
            topAiringAnimesUseCase.run(any())
            animeDetailUseCase.runAsync(any()).await()
            animeDetailErrorCaptured()
        }

        TestLogger.finally("Validate every method called from dependee use cases were verified")
        confirmVerified(topAiringAnimesUseCase, animeDetailUseCase)
    }

    @Test
    fun `fetch recommended anime then anime videos fails`() = runBlockingTest {
        TestLogger.given("Dependee use cases return mocked data")
        coEvery { topAiringAnimesUseCase.run(any()) } returns dummyDomainTopAnimesResultSuccess
        coEvery { animeDetailUseCase.runAsync(any()).await() } returns dummyDomainAnimeDetailResultSuccess
        coEvery { animeVideosUseCase.runAsync(any()).await() } returns dummyDomainAnimeVideosResultError

        TestLogger.and("ResultWrapper returns mocked data")
        every { topAnimesSuccessCaptured() } returns ANIME_ID.toInt()
        every { animeDetailSuccessCaptured() } returns dummyAnimeDetailMapped
        every { animeVideosErrorCaptured() } throws dummyWrappedException

        TestLogger.whenever("FetchRecommendedAnimeUseCase is executed")
        val result = useCase.run(Unit)
        animeIdSlot.captured.invoke(dummyTopAnimesMapped)
        animeDetailSlot.captured.invoke(dummyAnimeDetailMapped)
        throwSlot.captured.invoke(dummyBusinessError)

        TestLogger.then("Validate that the expected data is returned")
        result.error()?.get() shouldEqual dummyBusinessError

        TestLogger.then("Validate dependee use cases were called")
        coVerifyAll {
            topAiringAnimesUseCase.run(any())
            animeDetailUseCase.runAsync(any()).await()
            animeVideosUseCase.runAsync(any()).await()

            animeDetailSuccessCaptured()
            animeVideosErrorCaptured()
        }

        TestLogger.finally("Validate every method called from dependee use cases were verified")
        confirmVerified(topAiringAnimesUseCase, animeDetailUseCase, animeVideosUseCase)
    }

    private fun MockKMatcherScope.topAnimesSuccessCaptured() =
        dummyDomainTopAnimesResultSuccess.returnSuccessOrThrow(
            capture(animeIdSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.animeDetailSuccessCaptured() =
        dummyDomainAnimeDetailResultSuccess.returnSuccessOrThrow(
            capture(animeDetailSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.animeVideosSuccessCaptured() =
        dummyDomainAnimeVideosResultSuccess.returnSuccessOrThrow(
            capture(animeVideosSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.topAnimesErrorCaptured() =
        dummyDomainTopAnimesResultError.returnSuccessOrThrow(
            capture(animeIdSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.animeDetailErrorCaptured() =
        dummyDomainAnimeDetailResultError.returnSuccessOrThrow(
            capture(animeDetailSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.animeVideosErrorCaptured() =
        dummyDomainAnimeVideosResultError.returnSuccessOrThrow(
            capture(animeVideosSlot),
            capture(throwSlot)
        )
}
