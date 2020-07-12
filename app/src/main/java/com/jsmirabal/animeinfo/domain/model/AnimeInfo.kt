package com.jsmirabal.animeinfo.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeInfo(
    @SerializedName("mal_id")
    val id: Int,
    val rank: Int,
    val title: String?,
    val url: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val type: String?,
    val episodes: Int,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    val members: Int,
    val score: Float
) : Parcelable
