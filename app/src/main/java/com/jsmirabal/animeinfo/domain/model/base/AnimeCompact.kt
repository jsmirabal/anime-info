package com.jsmirabal.animeinfo.domain.model.base

data class AnimeCompact(
    private val definition: AnimeDefinition,
    private val extension: AnimeExtension
) : AnimeDefinition by definition, AnimeExtension by extension
