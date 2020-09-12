package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.service.model.DataGenericAnime
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.model.*

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
}
