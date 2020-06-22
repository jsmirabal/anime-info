package com.jsmirabal.animeinfo.data

interface AnimeService {
    suspend fun fetchTopItems(
        type: String,
        subType: String = "", // Optional
        page: String = "" // Optional
    ): AnimeServiceModel
}
