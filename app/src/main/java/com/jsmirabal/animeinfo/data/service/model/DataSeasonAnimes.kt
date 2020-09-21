package com.jsmirabal.animeinfo.data.service.model

import com.google.gson.annotations.SerializedName

data class DataSeasonAnimes(
    @SerializedName("season_name")
    val seasonName: String,
    @SerializedName("season_year")
    val seasonYear: String,
    @SerializedName("anime")
    val animes: List<DataGenericAnime>
)
