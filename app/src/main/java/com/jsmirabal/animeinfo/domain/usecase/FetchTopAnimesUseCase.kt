package com.jsmirabal.animeinfo.domain.usecase

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import kotlinx.coroutines.CoroutineScope

class FetchTopAnimesUseCase(
    private val repository: AnimeRepository,
    scope: CoroutineScope
) : UseCase<String, ResultWrapper<DomainTopAnimes, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(pageNumber: String) = repository.fetchTopAiringAnimes(pageNumber)
}
