package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import com.jsmirabal.animeinfo.presentation.di.DomainCoroutine
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchCurrentSeasonUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    @DomainCoroutine
    scope: CoroutineScope
) : UseCase<Unit, ResultWrapper<DomainSeasonAnimes, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(param: Unit) = animeRepository.fetchCurrentSeason()
}
