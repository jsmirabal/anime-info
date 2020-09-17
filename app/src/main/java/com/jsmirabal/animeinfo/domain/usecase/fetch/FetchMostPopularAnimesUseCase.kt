package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import com.jsmirabal.animeinfo.presentation.di.DomainCoroutine
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchMostPopularAnimesUseCase @Inject constructor(
    private val repository: AnimeRepository,
    @DomainCoroutine
    scope: CoroutineScope
) : UseCase<String, ResultWrapper<DomainTopAnimes, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(page: String) = repository.fetchMostPopularAnimes(page)
}
