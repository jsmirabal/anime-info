package com.jsmirabal.animeinfo.data.repository

import com.jsmirabal.animeinfo.data.service.AnimeService
import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.NO_PAGE
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
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
        Top.Type.ANIME,
        Top.SubType.AIRING,
        page
    ).let { handleTopItems(it) }

    override suspend fun fetchTopUpcomingAnimes(page: String) = animeService.fetchTopItems(
        Top.Type.ANIME,
        Top.SubType.UPCOMING,
        page
    ).let { handleTopItems(it) }

    override suspend fun fetchMostPopularAnimes(page: String) = animeService.fetchTopItems(
        Top.Type.ANIME,
        Top.SubType.BY_POPULARITY,
        page
    ).let { handleTopItems(it) }

    override suspend fun fetchMostFavoriteAnimes(page: String) = animeService.fetchTopItems(
        Top.Type.ANIME,
        Top.SubType.FAVORITE,
        page
    ).let { handleTopItems(it) }

    override suspend fun fetchAnimeDetail(id: String) = animeService.fetchAnime(
        id,
        Anime.Request.DETAIL,
        NO_PAGE
    ).let {
        when (val result = it) {
            is Success -> Success(mapper.mapToAnimeDetail(result.get()))
            is Error -> getError(result)
        }
    }

    override suspend fun fetchAnimeVideos(id: String) = animeService.fetchAnime(
        id,
        Anime.Request.VIDEOS,
        NO_PAGE
    ).let {
        when (val result = it) {
            is Success -> Success(mapper.mapToAnimeVideos(result.get()))
            is Error -> getError(result)
        }
    }

    override suspend fun fetchCurrentSeason() = animeService.fetchSeason().let {
        when (val result = it) {
            is Success -> Success(mapper.mapToDomainSeasonAnimes(result.get()))
            is Error -> getError(result)
        }
    }

    private fun handleTopItems(result: ResultWrapper<DataTopItems, DataLayerError>) =
        when (result) {
            is Success -> Success(mapper.mapToTopAnimes(result.get()))
            is Error -> getError(result)
        }

    private fun getError(result: Error<DataLayerError>) =
        Error(DomainLayerError.DelegateError(result.get()))
}
