package com.jsmirabal.animeinfo.domain.usecase.fetch

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository
import com.jsmirabal.animeinfo.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineScope

class FetchAnimeVideosUseCase(
    private val animeRepository: AnimeRepository,
    scope: CoroutineScope
) : UseCase<String, ResultWrapper<DomainAnimeVideos, DomainLayerError>>(scope) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun run(animeId: String) = animeRepository.fetchAnimeVideos(animeId)
}
