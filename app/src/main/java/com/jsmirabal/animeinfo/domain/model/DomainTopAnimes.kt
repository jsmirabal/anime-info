package com.jsmirabal.animeinfo.domain.model

import com.jsmirabal.animeinfo.domain.model.base.AnimeCompact
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType

data class DomainTopAnimes(
    val topAnimes: List<AnimeCompact>,
    override val feedItemType: MainFeedItemType
) : MainFeedItem
