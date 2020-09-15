package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.service.model.DataGenericAnime
import com.jsmirabal.animeinfo.data.service.model.DataSeasonAnimes
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.model.AnimeCompact
import com.jsmirabal.animeinfo.domain.model.AnimeDefinitionImpl
import com.jsmirabal.animeinfo.domain.model.AnimeExtensionImpl
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes

class AnimeMapper(private val gson: Gson) {

    fun mapToTopAnimes(data: DataTopItems) = DomainTopAnimes(
        data.topItems.map {
            gson.toJson(it).run {
                AnimeCompact(
                    gson.fromJson(this, AnimeDefinitionImpl::class.java),
                    gson.fromJson(this, AnimeExtensionImpl::class.java)
                )
            }
        }
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
        }
    )
}
