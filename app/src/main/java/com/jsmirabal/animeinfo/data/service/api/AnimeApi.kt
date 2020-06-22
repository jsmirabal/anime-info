package com.jsmirabal.animeinfo.data.service.api

import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    enum class Type(private val value: String) {
        ANIME("anime"),
        MANGA("manga"),
        PEOPLE("people"),
        CHARACTERS("characters");

        fun get() = value
    }

    enum class SubType(private val value: String) {
        // Anime
        AIRING("airing"), UPCOMING("upcoming"), TV("tv"),
        MOVIE("movie"), OVA("ova"), SPECIAL("special"),

        // Manga
        MANGA("manga"), NOVELS("novels"), ONESHOTS("oneshots"),
        DOUJIN("doujin"), MANHWA("manhwa"), MANHUA("manhua"),

        // Both
        BYPOPULARITY("bypopularity"),
        FAVORITE("favorite"),

        NO_SUB_TYPE("");

        fun get() = value
    }

    @GET("/v3/top/{type}/{page}/{subType}")
    suspend fun fetchTopItems(
        @Path("type") type: String,
        @Path("subType") subType: String,
        @Path("page") page: String
    ): DataTopItems
}
