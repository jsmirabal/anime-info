package com.jsmirabal.animeinfo.data.service.api

import com.jsmirabal.animeinfo.data.service.model.DataGenericAnime
import com.jsmirabal.animeinfo.data.service.model.DataSeasonAnimes
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("/top/{type}/{page}/{subType}")
    suspend fun fetchTopItems(
        @Path("type") type: String,
        @Path("subType") subType: String,
        @Path("page") page: String
    ): DataTopItems

    @GET("/anime/{id}/{request}/{page}")
    suspend fun fetchAnime(
        @Path("id") id: String,
        @Path("request") request: String,
        @Path("page") page: String
    ): DataGenericAnime

    @GET("/season/{year}/{season}")
    suspend fun fetchSeason(
        @Path("year") year: String,
        @Path("season") season: String
    ): DataSeasonAnimes
}
