package com.jsmirabal.animeinfo.domain.repository

import com.jsmirabal.animeinfo.data.service.api.NO_PAGE
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes

interface AnimeRepository {

    suspend fun fetchTopAiringAnimes(
        page: String = NO_PAGE // Optional
    ): ResultWrapper<DomainTopAnimes, DomainLayerError>

    suspend fun fetchAnimeDetail(id: String): ResultWrapper<DomainAnimeDetail, DomainLayerError>
}
