package com.jsmirabal.animeinfo.domain.usecase

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineScope

class FetchCurrentSeasonUseCase(
    private val animeRepository: AnimeRepository,
    scope: CoroutineScope
) : UseCase<Unit, ResultWrapper<DomainSeasonAnimes, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(param: Unit) = animeRepository.fetchCurrentSeason()
}
