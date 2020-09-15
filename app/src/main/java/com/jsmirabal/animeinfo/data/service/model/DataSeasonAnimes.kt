package com.jsmirabal.animeinfo.data.service.model

data class DataSeasonAnimes(
    val seasonName: String,
    val seasonYear: String,
    val animes: List<DataGenericAnime>
)
