package com.jsmirabal.animeinfo.data.service

import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper

interface AnimeService {
    suspend fun fetchTopItems(
        type: AnimeApi.Type,
        subType: AnimeApi.SubType = AnimeApi.SubType.NO_SUB_TYPE, // Optional
        page: String = "1"
    ): ResultWrapper<DataTopItems, DataLayerError>
}