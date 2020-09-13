package com.jsmirabal.animeinfo.domain.model

data class DomainRecommendedAnime(
    private val definition: AnimeDefinition,
    val synopsis: String,
    val trailers: List<DomainAnimeVideos.Trailer>
) : AnimeDefinition by definition
