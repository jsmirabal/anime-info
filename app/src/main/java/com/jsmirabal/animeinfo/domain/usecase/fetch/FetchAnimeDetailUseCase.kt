package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import com.jsmirabal.animeinfo.presentation.di.DomainCoroutine
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class FetchAnimeDetailUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    @DomainCoroutine
    scope: CoroutineScope
) : UseCase<String, ResultWrapper<DomainAnimeDetail, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(animeId: String) = animeRepository.fetchAnimeDetail(animeId)
}
