package com.jsmirabal.animeinfo.data.repository

import com.jsmirabal.animeinfo.data.service.AnimeService
import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Error
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Success
import com.jsmirabal.animeinfo.domain.mapper.AnimeMapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val animeService: AnimeService,
    private val mapper: AnimeMapper
) : AnimeRepository {

    override suspend fun fetchTopAiringAnimes(page: String): ResultWrapper<DomainTopAnimes, DomainLayerError> =
        animeService.fetchTopItems(
            type = AnimeApi.Type.ANIME,
            subType = AnimeApi.SubType.AIRING,
            page = page
        ).let {
            when(val result = it) {
                is Success -> Success(mapper.mapToTopAnimes(result.get()))
                is Error -> Error(DomainLayerError.DelegateError(result.get()))
            }
        }
}
