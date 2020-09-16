package com.jsmirabal.animeinfo.domain.usecase.build

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Error
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Success
import com.jsmirabal.animeinfo.domain.core.WrappedException
import com.jsmirabal.animeinfo.domain.core.getRandomNumber
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainRecommendedAnime
import com.jsmirabal.animeinfo.domain.model.base.AnimeDefinitionImpl
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchAnimeDetailUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchAnimeVideosUseCase
import com.jsmirabal.animeinfo.domain.usecase.fetch.FetchTopAiringAnimesUseCase
import kotlinx.coroutines.CoroutineScope

class BuildRecommendedAnimeUseCase(
    private val fetchTopAiringAnimesUseCase: FetchTopAiringAnimesUseCase,
    private val fetchAnimeDetailUseCase: FetchAnimeDetailUseCase,
    private val fetchAnimeVideosUseCase: FetchAnimeVideosUseCase,
    scope: CoroutineScope
) : UseCase<Unit, ResultWrapper<DomainRecommendedAnime, DomainLayerError>>(scope) {

    override suspend fun run(param: Unit) = try {
        val animeId = getAnimeIdOrFail()
        val animeDetail = getAnimeDetailOrFail(animeId)
        val animeVideos = getAnimeVideosOrFail(animeId)

        Success(
            DomainRecommendedAnime(
                AnimeDefinitionImpl(animeId, animeDetail.title, animeDetail.imageUrl),
                animeDetail.synopsis,
                animeVideos.trailers,
                MainFeedItemType.RECOMMENDED
            )
        )
    } catch (e: WrappedException) {
        Error(e.error as DomainLayerError)
    }

    private suspend fun getAnimeIdOrFail(): Int =
        fetchTopAiringAnimesUseCase.run(pageNumber = "1").returnSuccessOrThrow(
            returnSuccess = {
                it.topAnimes.run { this[getRandomNumber(0, this.lastIndex)].id }
            },
            throwE = { WrappedException(it) }
        )

    private suspend fun getAnimeDetailOrFail(animeId: Int): DomainAnimeDetail =
        fetchAnimeDetailUseCase.runAsync(animeId.toString()).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )

    private suspend fun getAnimeVideosOrFail(animeId: Int): DomainAnimeVideos =
        fetchAnimeVideosUseCase.runAsync(animeId.toString()).await().returnSuccessOrThrow(
            returnSuccess = { it },
            throwE = { WrappedException(it) }
        )
}
