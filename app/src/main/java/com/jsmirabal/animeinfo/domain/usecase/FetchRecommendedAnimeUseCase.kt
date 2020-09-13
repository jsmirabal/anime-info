package com.jsmirabal.animeinfo.domain.usecase

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Error
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Success
import com.jsmirabal.animeinfo.domain.core.WrappedException
import com.jsmirabal.animeinfo.domain.core.getRandomNumber
import com.jsmirabal.animeinfo.domain.model.AnimeDefinitionImpl
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainRecommendedAnime
import kotlinx.coroutines.CoroutineScope

class FetchRecommendedAnimeUseCase(
    private val fetchTopAnimesUseCase: FetchTopAnimesUseCase,
    private val fetchAnimeDetailUseCase: FetchAnimeDetailUseCase,
    private val fetchAnimeVideosUseCase: FetchAnimeVideosUseCase,
    scope: CoroutineScope
) : UseCase<String, ResultWrapper<DomainRecommendedAnime, DomainLayerError>>(scope) {

    override suspend fun run(param: String): ResultWrapper<DomainRecommendedAnime, DomainLayerError> {
        return try {
            val animeId = getAnimeIdOrFail()
            val animeDetail = getAnimeDetailOrFail(animeId)
            val animeVideos = getAnimeVideosOrFail(animeId)

            Success(
                DomainRecommendedAnime(
                    AnimeDefinitionImpl(animeId, animeDetail.title, animeDetail.imageUrl),
                    animeDetail.synopsis,
                    animeVideos.trailers
                )
            )
        } catch (e: WrappedException) {
            Error(e.error as DomainLayerError)
        }
    }

    private suspend fun getAnimeIdOrFail(): Int =
        fetchTopAnimesUseCase.run(pageNumber = "1").returnSuccessOrThrow(
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
