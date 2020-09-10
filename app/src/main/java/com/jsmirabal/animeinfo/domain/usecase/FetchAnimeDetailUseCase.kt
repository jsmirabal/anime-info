package com.jsmirabal.animeinfo.domain.usecase

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.AnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import kotlinx.coroutines.CoroutineScope

class FetchAnimeDetailUseCase(
    private val animeRepository: AnimeRepository,
    scope: CoroutineScope
) : UseCase<String, ResultWrapper<AnimeDetail, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(animeId: String) = animeRepository.fetchAnimeDetail(animeId)
}
