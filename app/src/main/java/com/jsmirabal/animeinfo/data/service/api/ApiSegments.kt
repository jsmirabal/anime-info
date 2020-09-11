package com.jsmirabal.animeinfo.data.service.api

const val NO_PAGE = ""

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

object Anime {
    enum class Request(private val value: String) {
        DETAIL(""),
        CHARACTER_STAFF("character_staff"),
        EPISODES("episodes"),
        NEWS("news"),
        PICTURES("pictures"),
        VIDEOS("videos"),
        STATS("stats"),
        FORUM("forum"),
        MORE_INFO("moreinfo"),
        REVIEW("review"),
        RECOMMENDATION("recommendations"),
        USER_UPDATES("userupdates");

        fun get() = value
    }
}
