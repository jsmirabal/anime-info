package com.jsmirabal.animeinfo.data

import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    enum class Type(value: String) {
        ANIME("anime"),
        MANGA("manga"),
        PEOPLE("people"),
        CHARACTERS("characters")
    }

    enum class SubType(value: String) {
        // Anime
        AIRING("airing"), UPCOMING("upcoming"), TV("tv"),
        MOVIE("movie"), OVA("ova"), SPECIAL("special"),

        // Manga
        MANGA("manga"), NOVELS("novels"), ONESHOTS("oneshots"),
        DOUJIN("doujin"), MANHWA("manhwa"), MANHUA("manhua"),

        // Both
        BYPOPULARITY("bypopularity"),
        FAVORITE("favorite")
    }

    @GET("/v3/top/{type}/{page}/{subType}")
    suspend fun fetchTopItems(
        @Path("type") type: String,
        @Path("subType") subType: String,
        @Path("page") page: String
    ): AnimeServiceModel
}
