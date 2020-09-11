package com.jsmirabal.animeinfo.data.service

import com.google.gson.internal.LinkedTreeMap
import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.NO_PAGE
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
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
    ): ResultWrapper<LinkedTreeMap<Any, Any>, DataLayerError>
}
