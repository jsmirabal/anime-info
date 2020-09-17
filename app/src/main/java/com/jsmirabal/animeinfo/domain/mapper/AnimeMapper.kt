package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.service.model.DataGenericAnime
import com.jsmirabal.animeinfo.data.service.model.DataSeasonAnimes
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.model.base.AnimeCompact
import com.jsmirabal.animeinfo.domain.model.base.AnimeDefinitionImpl
import com.jsmirabal.animeinfo.domain.model.base.AnimeExtensionImpl
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType
import javax.inject.Inject

class AnimeMapper @Inject constructor(
    private val gson: Gson
) {

    fun mapToTopAnimes(data: DataTopItems, feedItemType: MainFeedItemType) = DomainTopAnimes(
        data.topItems.map {
            gson.toJson(it).run {
                AnimeCompact(
                    gson.fromJson(this, AnimeDefinitionImpl::class.java),
                    gson.fromJson(this, AnimeExtensionImpl::class.java)
                )
            }
        },
        feedItemType
    )

    fun mapToAnimeDetail(data: DataGenericAnime): DomainAnimeDetail =
        gson.toJson(data).let { json ->
            gson.fromJson(json, DomainAnimeDetail::class.java).let { detail ->
                DomainAnimeDetail(
                    gson.fromJson(json, AnimeDefinitionImpl::class.java),
                    gson.fromJson(json, AnimeExtensionImpl::class.java),
                    synopsis = detail.synopsis
                )
            }
        }

    fun mapToAnimeVideos(data: DataGenericAnime): DomainAnimeVideos {
        val episodes = (data["episodes"] as List<*>).map { episode ->
            gson.fromJson(gson.toJson(episode), DomainAnimeVideos.Episode::class.java).run {
                DomainAnimeVideos.Episode(
                    gson.fromJson(gson.toJson(episode), AnimeDefinitionImpl::class.java),
                    this.url,
                    this.episode
                )
            }
        }
        val trailers = (data["promo"] as List<*>).map { trailer ->
            gson.fromJson(gson.toJson(trailer), DomainAnimeVideos.Trailer::class.java).run {
                DomainAnimeVideos.Trailer(
                    gson.fromJson(gson.toJson(trailer), AnimeDefinitionImpl::class.java),
                    this.videoUrl
                )
            }
        }

        return DomainAnimeVideos(episodes, trailers)
    }

    fun mapToDomainSeasonAnimes(data: DataSeasonAnimes) = DomainSeasonAnimes(
        data.seasonName,
        data.seasonYear,
        data.animes.map {
            gson.toJson(it).run {
                AnimeCompact(
                    gson.fromJson(this, AnimeDefinitionImpl::class.java),
                    gson.fromJson(this, AnimeExtensionImpl::class.java)
                )
            }
        },
        MainFeedItemType.CURRENT_SEASON
    )
}
