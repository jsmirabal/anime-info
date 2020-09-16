package com.jsmirabal.animeinfo.domain.model

import com.jsmirabal.animeinfo.domain.model.base.AnimeCompact
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType

data class DomainSeasonAnimes(
    val seasonName: String,
    val seasonYear: String,
    val animes: List<AnimeCompact>,
    override val feedItemType: MainFeedItemType
) : MainFeedItem
