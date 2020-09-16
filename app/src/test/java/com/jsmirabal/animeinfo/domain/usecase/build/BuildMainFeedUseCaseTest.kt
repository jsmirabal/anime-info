package com.jsmirabal.animeinfo.domain.usecase.build

import com.jsmirabal.animeinfo.data.*
import com.jsmirabal.animeinfo.data.dummyDomainRecommendedAnimeSuccess
import com.jsmirabal.animeinfo.data.dummyDomainTopAnimesResultSuccess
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchCurrentSeasonUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchMostFavoriteAnimesUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchMostPopularAnimesUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchTopAiringAnimesUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchTopUpcomingAnimesUseCase
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
internal class BuildMainFeedUseCaseTest {
    private val testScope = TestCoroutineScope()

    private val successSlot = slot<(MainFeedItem) -> MainFeedItem>()
    private val throwSlot = slot<(DomainLayerError) -> Exception>()

    private val recommendedAnimeUseCase = mockk<BuildRecommendedAnimeUseCase>()
    private val currentSeasonUseCase = mockk<FetchCurrentSeasonUseCase>()
    private val topAiringUseCase = mockk<FetchTopAiringAnimesUseCase>()
    private val topUpcomingUseCase = mockk<FetchTopUpcomingAnimesUseCase>()
    private val mostPopularUseCase = mockk<FetchMostPopularAnimesUseCase>()
    private val mostFavoriteUseCase = mockk<FetchMostFavoriteAnimesUseCase>()
    private val useCase = BuildMainFeedUseCase(
        recommendedAnimeUseCase,
        currentSeasonUseCase,
        topAiringUseCase,
        topUpcomingUseCase,
        mostPopularUseCase,
        mostFavoriteUseCase,
        testScope
    )

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `build main feed then success`() = runBlockingTest {
        TestLogger.given("Dependee use cases return mocked data")
        coEvery { recommendedAnimeUseCase.runAsync(any()).await() } returns dummyDomainRecommendedAnimeSuccess
        coEvery { currentSeasonUseCase.runAsync(any()).await() } returns dummyDomainSeasonAnimesSuccess
        coEvery { topAiringUseCase.runAsync(any()).await() } returns dummyDomainTopAnimesResultSuccess
        coEvery { topUpcomingUseCase.runAsync(any()).await() } returns dummyDomainTopAnimesResultSuccess
        coEvery { mostPopularUseCase.runAsync(any()).await() } returns dummyDomainTopAnimesResultSuccess
        coEvery { mostFavoriteUseCase.runAsync(any()).await() } returns dummyDomainTopAnimesResultSuccess

        TestLogger.and("ResultWrapper returns mocked data")
        every { recommendedAnimeCaptured() } returns dummyMainFeedItem
        every { seasonCaptured() } returns dummyMainFeedItem
        every { topAnimesCaptured() } returns dummyMainFeedItem

        TestLogger.whenever("BuildMainFeedItemUseCase is executed")
        val result = useCase.run(Unit)

        TestLogger.then("Validate that the expected data is returned")
        result.success()?.get()?.feedItems?.size shouldEqual 6

        TestLogger.then("Validate dependee use cases were called")
        coVerifyAll {
            recommendedAnimeUseCase.runAsync(any()).await()
            currentSeasonUseCase.runAsync(any()).await()
            topAiringUseCase.runAsync(any()).await()
            topUpcomingUseCase.runAsync(any()).await()
            mostPopularUseCase.runAsync(any()).await()
            mostFavoriteUseCase.runAsync(any()).await()

            recommendedAnimeCaptured()
            seasonCaptured()
            topAnimesCaptured()
        }

        TestLogger.finally("Validate every method called from dependee use cases were verified")
        confirmVerified(
            recommendedAnimeUseCase,
            currentSeasonUseCase,
            topAiringUseCase,
            topUpcomingUseCase,
            mostPopularUseCase,
            mostFavoriteUseCase
        )
    }

    private fun MockKMatcherScope.recommendedAnimeCaptured() =
        dummyDomainRecommendedAnimeSuccess.returnSuccessOrThrow(
            capture(successSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.seasonCaptured() =
        dummyDomainSeasonAnimesSuccess.returnSuccessOrThrow(
            capture(successSlot),
            capture(throwSlot)
        )

    private fun MockKMatcherScope.topAnimesCaptured() =
        dummyDomainTopAnimesResultSuccess.returnSuccessOrThrow(
            capture(successSlot),
            capture(throwSlot)
        )

}
