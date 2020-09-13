package com.jsmirabal.animeinfo.domain.model

data class DomainSeasonAnimes(
    val seasonName: String,
    val seasonYear: String,
    val animes: List<AnimeCompact>
)
