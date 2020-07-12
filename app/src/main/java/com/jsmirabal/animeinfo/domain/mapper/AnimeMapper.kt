package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.model.AnimeInfo
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes

class AnimeMapper(private val gson: Gson) {

    fun mapToTopAnimes(data: DataTopItems) = DomainTopAnimes(
        data.topItems.map {
            gson.fromJson(gson.toJson(it), AnimeInfo::class.java)
        }
    )
}
