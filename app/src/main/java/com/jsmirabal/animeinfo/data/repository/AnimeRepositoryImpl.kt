package com.jsmirabal.animeinfo.data.repository

import com.jsmirabal.animeinfo.data.service.AnimeService
import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.NO_PAGE
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Error
import com.jsmirabal.animeinfo.domain.core.ResultWrapper.Success
import com.jsmirabal.animeinfo.domain.mapper.AnimeMapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.repository.AnimeRepository

class AnimeRepositoryImpl(
    private val animeService: AnimeService,
    private val mapper: AnimeMapper
) : AnimeRepository {

    override suspend fun fetchTopAiringAnimes(page: String) = animeService.fetchTopItems(
        type = Top.Type.ANIME,
        subType = Top.SubType.AIRING,
        page = page
    ).let {
        when (val result = it) {
            is Success -> Success(mapper.mapToTopAnimes(result.get()))
            is Error -> Error(DomainLayerError.DelegateError(result.get()))
        }
    }

    override suspend fun fetchAnimeDetail(id: String) = animeService.fetchAnime(
        id,
        Anime.Request.DETAIL,
        page = NO_PAGE
    ).let {
        when (val result = it) {
            is Success -> Success(mapper.mapToAnimeDetail(result.get()))
            is Error -> Error(DomainLayerError.DelegateError(result.get()))
        }
    }
}
