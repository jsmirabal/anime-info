package com.jsmirabal.animeinfo.domain.repository

import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes

interface AnimeRepository {

    suspend fun fetchTopAiringAnimes(
        page: String = "" // Optional
    ): ResultWrapper<DomainTopAnimes, DomainLayerError>
}
