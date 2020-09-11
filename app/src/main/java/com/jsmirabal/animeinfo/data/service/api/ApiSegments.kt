package com.jsmirabal.animeinfo.data.service.api

object Top {
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
}
