package com.jsmirabal.animeinfo.domain.usecase.build

import com.jsmirabal.animeinfo.data.service.api.NO_PAGE
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Error
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Success
import com.jsmirabal.animeinfo.domain.core.WrappedException
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.mainfeed.DomainMainFeed
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchCurrentSeasonUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchMostFavoriteAnimesUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchMostPopularAnimesUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchTopAiringAnimesUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchTopUpcomingAnimesUseCase
import com.jsmirabal.animeinfo.presentation.di.DomainCoroutine
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class BuildMainFeedUseCase @Inject constructor(
    private val buildRecommendedAnimeUseCase: BuildRecommendedAnimeUseCase,
    private val fetchCurrentSeasonUseCase: FetchCurrentSeasonUseCase,
    private val fetchTopAiringAnimesUseCase: FetchTopAiringAnimesUseCase,
    private val fetchTopUpcomingAnimesUseCase: FetchTopUpcomingAnimesUseCase,
    private val fetchMostPopularAnimesUseCase: FetchMostPopularAnimesUseCase,
    private val fetchMostFavoriteAnimesUseCase: FetchMostFavoriteAnimesUseCase,
    @DomainCoroutine
    scope: CoroutineScope
) : UseCase<Unit, ResultWrapper<DomainMainFeed, DomainLayerError>>(scope) {

    override suspend fun run(param: Unit) = try {
        val recommendedAnime = getRecommendedAnimeOrFail()
        val currentSeason = getCurrentSeasonOrFail()
        val topAiringAnimes = getTopAiringAnimesOrFail()
        val topUpcomingAnimes = getTopUpcomingAnimesOrFail()
        val mostPopularAnimes = getMostPopularAnimesOrFail()
        val mostFavoriteAnimes = getMostFavoriteAnimesOrFail()

        Success(
            DomainMainFeed(
                listOf(
                    recommendedAnime,
                    currentSeason,
                    topAiringAnimes,
                    topUpcomingAnimes,
                    mostPopularAnimes,
                    mostFavoriteAnimes
                )
            )
        )

    } catch (e: WrappedException) {
        Error(e.error as DomainLayerError)
    }

    private suspend fun getRecommendedAnimeOrFail(): MainFeedItem =
        buildRecommendedAnimeUseCase.runAsync(Unit).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )

    private suspend fun getCurrentSeasonOrFail(): MainFeedItem =
        fetchCurrentSeasonUseCase.runAsync(Unit).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )

    private suspend fun getTopAiringAnimesOrFail(): MainFeedItem =
        fetchTopAiringAnimesUseCase.runAsync(NO_PAGE).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )

    private suspend fun getTopUpcomingAnimesOrFail(): MainFeedItem =
        fetchTopUpcomingAnimesUseCase.runAsync(NO_PAGE).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )

    private suspend fun getMostPopularAnimesOrFail(): MainFeedItem =
        fetchMostPopularAnimesUseCase.runAsync(NO_PAGE).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )

    private suspend fun getMostFavoriteAnimesOrFail(): MainFeedItem =
        fetchMostFavoriteAnimesUseCase.runAsync(NO_PAGE).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )
}
