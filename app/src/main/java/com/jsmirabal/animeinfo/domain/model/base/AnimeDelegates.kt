package com.jsmirabal.animeinfo.domain.model.base

import com.google.gson.annotations.SerializedName

interface AnimeDefinition {
    val id: Int
    val title: String?
    val imageUrl: String?
}

interface AnimeExtension {
    val rank: Int
    val url: String?
    val type: String?
    val episodes: Int
    val startDate: String?
    val endDate: String?
    val members: Int
    val score: Float
}

data class AnimeDefinitionImpl(
    @SerializedName("mal_id")
    override val id: Int,
    override val title: String?,
    @SerializedName("image_url")
    override val imageUrl: String?
) : AnimeDefinition

data class AnimeExtensionImpl(
    override val rank: Int,
    override val url: String?,
    override val type: String?,
    override val episodes: Int,
    @SerializedName("start_date")
    override val startDate: String?,
    @SerializedName("end_date")
    override val endDate: String?,
    override val members: Int,
    override val score: Float
) : AnimeExtension
