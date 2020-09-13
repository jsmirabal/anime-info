package com.jsmirabal.animeinfo.data.service

import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.NO_PAGE
import com.jsmirabal.animeinfo.data.service.api.NO_YEAR
import com.jsmirabal.animeinfo.data.service.api.Season
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataGenericAnime
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.data.service.model.DataSeasonAnimes
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper

interface AnimeService {
    suspend fun fetchTopItems(
        type: Top.Type,
        subType: Top.SubType = Top.SubType.NO_SUB_TYPE, // Optional
        page: String = NO_PAGE // Optional
    ): ResultWrapper<DataTopItems, DataLayerError>

    suspend fun fetchAnime(
        id: String,
        request: Anime.Request,
        page: String = NO_PAGE // Optional
    ): ResultWrapper<DataGenericAnime, DataLayerError>

    suspend fun fetchSeason(
        year: String = NO_YEAR,// Optional
        season: Season = Season.CURRENT_SEASON // Default
    ): ResultWrapper<DataSeasonAnimes, DataLayerError>
}
