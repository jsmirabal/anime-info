package com.jsmirabal.animeinfo.domain.model

import com.google.gson.annotations.SerializedName
import com.jsmirabal.animeinfo.domain.model.base.AnimeDefinition

data class DomainAnimeVideos(
    val episodes: List<Episode>,
    @SerializedName("promo")
    val trailers: List<Trailer>) {

    data class Episode(
        val definition: AnimeDefinition,
        val url: String,
        val episode: String
    ) : AnimeDefinition by definition

    data class Trailer(
        val definition: AnimeDefinition,
        @SerializedName("video_url")
        val videoUrl: String
    ) : AnimeDefinition by definition
}
