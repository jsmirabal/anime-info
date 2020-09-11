package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
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

    fun mapToAnimeDetail(data: LinkedTreeMap<Any, Any>): DomainAnimeDetail =
        gson.toJson(data).let { json ->
            gson.fromJson(json, DomainAnimeDetail::class.java).let { detail ->
                DomainAnimeDetail(
                    gson.fromJson(json, AnimeDefinitionImpl::class.java),
                    gson.fromJson(json, AnimeExtensionImpl::class.java),
                    synopsis = detail.synopsis
                )
            }
        }
}
