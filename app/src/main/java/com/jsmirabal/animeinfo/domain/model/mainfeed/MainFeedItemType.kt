package com.jsmirabal.animeinfo.domain.model.mainfeed

enum class MainFeedItemType(private val type: Int) {
    RECOMMENDED(101),
    CURRENT_SEASON(102),
    TOP_AIRING_ANIMES(103),
    TOP_UPCOMING_ANIMES(104),
    MOST_POPULAR_ANIMES(105),
    MOST_FAVORITE_ANIMES(106);

    fun get() = type
}
