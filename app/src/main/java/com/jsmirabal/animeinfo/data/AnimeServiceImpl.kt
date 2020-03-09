package com.jsmirabal.animeinfo.data

import retrofit2.Retrofit

class AnimeServiceImpl(private val retrofit: Retrofit) : AnimeService {

    override suspend fun fetchTopItems(type: String, subType: String, page: String) =
        retrofit.create(AnimeApi::class.java).fetchTopItems(type, subType, page)
}
