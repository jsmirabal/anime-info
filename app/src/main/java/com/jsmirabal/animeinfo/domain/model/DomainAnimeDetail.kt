package com.jsmirabal.animeinfo.domain.model

data class DomainAnimeDetail(
    private val definition: AnimeDefinition,
    private val extension: AnimeExtension,
    val synopsis: String
) : AnimeDefinition by definition, AnimeExtension by extension
