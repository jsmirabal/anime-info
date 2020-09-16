package com.jsmirabal.animeinfo.domain.model

import com.jsmirabal.animeinfo.domain.model.base.AnimeDefinition
import com.jsmirabal.animeinfo.domain.model.base.AnimeExtension

data class DomainAnimeDetail(
    private val definition: AnimeDefinition,
    private val extension: AnimeExtension,
    val synopsis: String
) : AnimeDefinition by definition, AnimeExtension by extension
