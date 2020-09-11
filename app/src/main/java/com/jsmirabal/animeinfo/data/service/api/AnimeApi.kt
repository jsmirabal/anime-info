package com.jsmirabal.animeinfo.data.service.api

import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.model.AnimeDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("/v3/top/{type}/{page}/{subType}")
    suspend fun fetchTopItems(
        @Path("type") type: String,
        @Path("subType") subType: String,
        @Path("page") page: String
    ): DataTopItems

    @GET("/v3/anime/{id}")
    suspend fun fetchAnimeDetail(
        @Path("id") id: String
    ): AnimeDetail
}
