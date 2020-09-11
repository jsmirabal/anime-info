package com.jsmirabal.animeinfo.data.service

import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.AnimeDetail

interface AnimeService {
    suspend fun fetchTopItems(
        type: Top.Type,
        subType: Top.SubType = Top.SubType.NO_SUB_TYPE, // Optional
        page: String = "1" // Optional
    ): ResultWrapper<DataTopItems, DataLayerError>

    suspend fun fetchAnimeDetail(id: String): ResultWrapper<AnimeDetail, DataLayerError>
}
