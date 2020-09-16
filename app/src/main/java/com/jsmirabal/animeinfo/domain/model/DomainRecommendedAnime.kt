package com.jsmirabal.animeinfo.domain.model

import com.jsmirabal.animeinfo.domain.model.base.AnimeDefinition
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType

data class DomainRecommendedAnime(
    private val definition: AnimeDefinition,
    val synopsis: String,
    val trailers: List<DomainAnimeVideos.Trailer>,
    override val feedItemType: MainFeedItemType
) : AnimeDefinition by definition, MainFeedItem
